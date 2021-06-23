package com.cominvi.app.oauth.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cominvi.app.commons.general.Usuariosexternos;
import com.cominvi.app.oauth.models.SmsResponse;
import com.cominvi.app.oauth.services.IAESEncryptionDecryption;
import com.cominvi.app.oauth.services.IUsuariosexternosService;
import com.cominvi.app.oauth.services.impl.SmsService;
import com.cominvi.app.oauth.utils.RandomString;
import com.cominvi.app.oauth.utils.Utils;

@RestController
@RequestMapping("/usuario-externo")
public class UsuarioExternoController {

	@Autowired
	private IUsuariosexternosService usuarioService;
	
	@Autowired
	private IAESEncryptionDecryption claveService;
	
	@Autowired
	private SmsService smsService;
	
	@GetMapping("/{idusuario}")
	public Usuariosexternos findById(@PathVariable Long idusuario) {		
		return usuarioService.findById(idusuario);
	}
	
	
	@PostMapping()
	public ResponseEntity<?> registrar(@RequestBody Usuariosexternos usuario) {
		
		Map<String, Object> map = new HashMap<>();
		Usuariosexternos usuarioNuevo = null;
		try {
			
			if( usuario.getRegistro() == 1 ) {
				// Buscamos usuario por telefono
				usuarioNuevo = usuarioService.findByCelular(usuario.getCelular());
			} else {
				// Buscamos usuario por correo
				usuarioNuevo = usuarioService.findByCorreo(usuario.getCorreo());
			}
			
			// Verificamos que el usuario no exista para brincar la siguiente validación
			if( usuarioNuevo != null ) {
				String mensaje = "El correo "+usuarioNuevo.getCorreo()+" ya se cuenta registrado";
				if( usuario.getRegistro() == 1 ) {
					mensaje = "El número de celular "+usuarioNuevo.getCelular()+" ya se cuenta registrado";
				}
				map.put("mensaje", mensaje);
				return new ResponseEntity<Map<String, Object>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			// Registro por celular
			if( usuario.getRegistro() == 1 ) { 
				// Instanciamos el objeto
				RandomString gen = new RandomString(5);
				// Generamos la clave
				String clave = gen.nextString().toUpperCase();
				// Enviamos el mensaje de texto
				SmsResponse sms = smsService.enviar(usuario.getCelular(), "[CoMinVi] Tu codigo de verificacion es " + clave);
				// Verificamos la respuesta
				if( sms == null || ( sms.getStatus().getCode() == 0 || sms.getStatus().getCode() > 3 ) ) {
					map.put("mensaje", "Error al intentar enviar el mensaje de texto, verifique que se haya ingresado un número valido");
					return new ResponseEntity<Map<String, Object>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
				}
				usuario.setClaveactivacion(clave);
				usuario.setFechavencimientoclave(usuarioService.agregaMinutos(60));
			}
			
			usuarioNuevo = usuarioService.save(usuario);
			
			if( usuario.getRegistro() == 2 ) {
				// Enviamos correo de confirmación
				String nombre = usuarioNuevo.getNombre() + " " + usuarioNuevo.getApepaterno() + " " + usuarioNuevo.getApematerno();
				String correo = usuarioNuevo.getCorreo();
				String clave = claveService.encrypt(usuario.getCorreo(), Utils.KEY_ACTIVACION);
				String activacion = usuarioService.getParametro((long)23) + "?clave=" +clave;
				String plantilla = Utils.regresaCuerpoEmailNuevoUsuario(nombre, correo, activacion);
				usuarioService.enviarCorreo(plantilla, correo, "Activa tu cuenta");
			}
			
			
		}catch (DataAccessException e) {
			map.put("mensaje", "Error al registrar la cuenta, intentelo nuevamente");
            map.put("error", e.getMostSpecificCause());
            return new ResponseEntity<Map<String, Object>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		map.put("mensaje", "Usuario registrado con éxito, revisa tu correo electrónico para activar la cuenta");
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.CREATED);
	}
	
	@PostMapping("/activar")
	public ResponseEntity<?> activar(@RequestParam("clave") String clave) {
		
		Map<String, Object> map = new HashMap<>();
		
		try {
			
			String correo = claveService.decrypt(clave, Utils.KEY_ACTIVACION);
			Usuariosexternos usuario = usuarioService.findByCorreo(correo);
			
			if( usuario == null ) {
				map.put("codigo", 100);
			}
			
			if(usuario != null && usuario.getEstatus() == 0) {
				map.put("codigo", 200);
				usuario.setEstatus(1);
				usuarioService.update(usuario, usuario.getIdusuarioexterno());
			} else if(usuario != null && usuario.getEstatus() > 0) {
				map.put("codigo", 300);
			}
			
			
			
		}catch (DataAccessException e) {
			map.put("mensaje", "Ocurrio un error al activar la cuenta");
            map.put("error", e.getMostSpecificCause());
            return new ResponseEntity<Map<String, Object>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	@PostMapping("/activa-clave")
	public ResponseEntity<?> activaClave(@RequestParam String clave, @RequestParam String celular) {
		
		Map<String, Object> map = new HashMap<>();
		try {
			
			map = usuarioService.activaByClave(clave, celular);
			if( Integer.parseInt(map.get("codigo").toString()) == 100 ) {
				return new ResponseEntity<Map<String, Object>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
			}
						
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}catch (DataAccessException e) {
			map.put("mensaje", "Ocurrio un error al activar la cuenta");
            map.put("error", e.getMostSpecificCause());
            return new ResponseEntity<Map<String, Object>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PostMapping("/recuperar-password")
	public ResponseEntity<?> recuperarPassword(@RequestParam String correo,
			@RequestParam String celular, @RequestParam int registro) {
		
		Map<String, Object> map = new HashMap<>();
		Usuariosexternos usuario = null;
		try {
			
			if( registro == 1 ) {
				usuario = usuarioService.findByCelular("+52" + celular);
			} else {
				usuario = usuarioService.findByCorreo(correo);
			}
			
			
			if( usuario == null ) {
				map.put("codigo", 300);
			} else {
				map.put("codigo", usuarioService.recuperarPassword(usuario, registro));
			}
			
			
		}catch (DataAccessException e) {
			map.put("mensaje", "No se pudo generar el codigo para reestablecer la contraseña, intentelo de nuevo");
            map.put("error", e.getMostSpecificCause());
            return new ResponseEntity<Map<String, Object>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		 return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	
	@PostMapping("/reestablecer-password")
	public ResponseEntity<?> reestablecer(@RequestParam String username, @RequestParam String codigo,
			@RequestParam String password, @RequestParam int registro) {
		
		Map<String, Object> map = new HashMap<>();
		
		try {
			map = usuarioService.reestablecer(username, codigo, password, registro);
		}catch (DataAccessException e) {
			map.put("mensaje", "No se pudo reestablecer la contraseña, intentelo de nuevo");
            map.put("error", e.getMostSpecificCause());
            return new ResponseEntity<Map<String, Object>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		
	}
	
	
	
}
