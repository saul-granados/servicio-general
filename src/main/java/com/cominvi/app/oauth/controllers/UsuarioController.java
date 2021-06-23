package com.cominvi.app.oauth.controllers;

import com.cominvi.app.commons.capitalhumano.Empleados;
import com.cominvi.app.commons.general.EmpleadosPerfiles;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cominvi.app.commons.general.Usuarios;
import com.cominvi.app.oauth.services.IEmpleadosPerfilesService;
import com.cominvi.app.oauth.services.IUsuarioService;
import com.cominvi.app.oauth.services.impl.EmailService;
import com.cominvi.app.oauth.utils.Utils;
import java.util.Date;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/usuario")
public class UsuarioController {


    @Autowired
    private IUsuarioService usuarioService;
    @Autowired
    private IEmpleadosPerfilesService empleadosperfileService;
    
    
    
    @GetMapping("/test")
    public String test() {
    	return usuarioService.test();
    }
    

    /**
     *
     * @return
     */
    @GetMapping("/carga-usuarios")
    public List<Map<String, Object>> cargaUsuarios() {
        return usuarioService.cargaUsuarios();
    }// cargaUsuarios()

    /**
     *
     * @param idempleado
     * @return
     */
    @GetMapping("/{idempleado}")
    public Usuarios buscarUsuarios(@PathVariable Long idempleado) {
        Usuarios usuario;
        try {
            usuario = usuarioService.findByIdEmpleado(idempleado);
        } catch (DataAccessException e) {
            return null;
        }
        return usuario;
    }// buscarUsuarios()

    /**
     *
     * @param numeronomina
     * @return
     */
    @GetMapping("/getexisteusuario/{numeronomina}")
    public boolean existeusuario(@PathVariable String numeronomina) {
        boolean existe;
        try {
            existe = usuarioService.existeusuario(numeronomina);
        } catch (DataAccessException e) {
            return true;
        }
        return existe;
    }// existeusuario()

    /**
     *
     * @param usuario
     * @return
     */
    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Usuarios usuario) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> p;
        EmailService emailService = new EmailService();
        Empleados empleado;
        String pass = usuario.getPassword();
        try {
            usuario = usuarioService.guardar(usuario);

            //<editor-fold defaultstate="collapsed" desc="Send Email">
            empleado = usuarioService.finByIdempleado(usuario.getIdempleado());
            p = usuarioService.cargaParametrosEmaill();
            String contenido = Utils.regresaCuerpoEmailOrdenCompra(empleado.getNumeronomina(), pass, empleado.getNombre());
            emailService.setProperties(p.get("host").toString(), p.get("puerto").toString(), p.get("correo").toString(), p.get("password").toString());
            emailService.sendEmail(p.get("correo").toString(), usuario.getCorreo(), "", "Alta de usuario", contenido, false, "");
            //</editor-fold>
        } catch (DataAccessException e) {
            map.put("mensaje", "No se pudo guardar la autorización");
            map.put("error", e.getMostSpecificCause());
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            //emailService = null;
        }
        map.put("mensaje", "El usuario se genero con éxito");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }// guardar()

    /**
     *
     * @param usuario
     * @param idempleado
     * @return
     */
    @PutMapping("/{idempleado}")
    public ResponseEntity<?> actualizar(
            @RequestBody Usuarios usuario, @PathVariable Long idempleado) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> p;
        Empleados empleado;
        EmailService emailService = new EmailService();
        try {
            usuarioService.update(usuario, idempleado);

            //<editor-fold defaultstate="collapsed" desc="Send Email">
            empleado = usuarioService.finByIdempleado(usuario.getIdempleado());
            String pass = usuario.getPassword();
            p = usuarioService.cargaParametrosEmaill();
            String contenido = Utils.regresaCuerpoEmailOrdenCompra(usuario.getUsuario(), pass, empleado.getNombre());
            emailService.setProperties(p.get("host").toString(), p.get("puerto").toString(), p.get("correo").toString(), p.get("password").toString());
            emailService.sendEmail(p.get("correo").toString(), usuario.getCorreo(), "", "Edición de usuario", contenido, false, "");
            //</editor-fold>
        } catch (DataAccessException e) {
            map.put("mensaje", "No se pudo editar el usuario");
            map.put("error", e.getMostSpecificCause());
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        map.put("mensaje", "El usuario se actualizo con éxito");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }// actualizar()

    /**
     *
     * @param usuario
     * @param idempleado
     * @param changepass
     * @return
     */
    @PutMapping("/{changepass}/{idempleado}")
    public ResponseEntity<?> actualizar(
            @RequestBody Usuarios usuario, @PathVariable Long idempleado, @PathVariable boolean changepass) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> p;
        EmailService emailService = new EmailService();
        String cambiosPass, pass = usuario.getPassword();
        Empleados empleado;
        try {
            usuarioService.updatepass(usuario, idempleado, changepass);

            //<editor-fold defaultstate="collapsed" desc="Send Email">                    
            if (changepass) {
                cambiosPass = "Contraseña: <strong>" + pass +"</strong>";// No encriptada
                empleado = usuarioService.finByIdempleado(idempleado);
                p = usuarioService.cargaParametrosEmaill();
                String contenido = Utils.regresaCuerpoEmailUpdateUser(empleado.getNumeronomina(), cambiosPass,empleado.getNombre());
                emailService.setProperties(p.get("host").toString(), p.get("puerto").toString(), p.get("correo").toString(), p.get("password").toString());
                emailService.sendEmail(p.get("correo").toString(), usuario.getCorreo(), "", "USUARIO PORTAL", contenido, false, "");
            }
            //</editor-fold>
        } catch (DataAccessException e) {
            map.put("mensaje", "No se pudo editar el usuario");
            map.put("error", e.getMostSpecificCause());
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        map.put("mensaje", "El usuario se actualizo con éxito");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }// actualizar()

    /**
     *
     * @param idempleado
     * @return
     */
    @PostMapping("/desbloquearsesionusuario")
    public ResponseEntity<?> desbloquearsesionusuario(
            @RequestParam Long idempleado) {
        Map<String, Object> map = new HashMap<>();
        Usuarios usuario;
        try {
            usuario = usuarioService.findByIdEmpleado(idempleado);
            usuario.setBloqueado((byte) 0);
            usuario.setIntentos((byte) 0);
            usuarioService.update(usuario, idempleado);
        } catch (DataAccessException e) {
            map.put("mensaje", "No se pudo desbloquear el usuario");
            map.put("error", e.getMostSpecificCause());
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }// desbloquearsesionusuario()

    /**
     * @param idsistema
     * @param idempresa
     * @param idempleado
     * @return
     */
    @PostMapping("/getlistadoperfilusuario")
    public ResponseEntity<?> getlistadoperfilusuario(@RequestParam Long idsistema, @RequestParam Long idempresa, @RequestParam Long idempleado) {
        List<Map<String, Object>> perfilusuario;
        try {
            perfilusuario = usuarioService.getlistadoperfilusuario(idsistema, idempresa, idempleado);
        } catch (DataAccessException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("mensaje", "Error");
            error.put("error", e.getMostSpecificCause().toString());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(perfilusuario, HttpStatus.OK);
    }//getlistadoperfilusuario()

    /**
     * @param idsistema
     * @param idempresa
     * @param idempleado
     * @return
     */
    @PostMapping("/getlistadoperfilsistema")
    public ResponseEntity<?> getlistadoperfilsistema(@RequestParam Long idsistema, @RequestParam Long idempresa, @RequestParam Long idempleado) {
        List<Map<String, Object>> perfilsistema;
        try {
            perfilsistema = usuarioService.getlistadoperfilsistema(idsistema, idempresa, idempleado);
        } catch (DataAccessException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("mensaje", "Error");
            error.put("error", e.getMostSpecificCause().toString());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(perfilsistema, HttpStatus.OK);
    }//getlistadoperfilsistema()

    /**
     *
     * @param idempleado
     * @param idperfil
     * @return
     */
    @PostMapping("/removeperfilusuario")
    public ResponseEntity<?> removeperfilusuario(@RequestParam long idempleado, @RequestParam long idperfil) {
        Boolean remove;
        try {
            remove = empleadosperfileService.removeById(idempleado, idperfil);
        } catch (DataAccessException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("mensaje", "Error al remover perfil");
            error.put("error", e.getMostSpecificCause().toString());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(remove, HttpStatus.OK);
    }//removeperfilusuario()

    /**
     *
     * @param idempleado
     * @param idperfil
     * @return
     */
    @PostMapping("/addperfilusuario")
    public ResponseEntity<?> addperfilusuario(@RequestParam long idempleado, @RequestParam long idperfil) {
        EmpleadosPerfiles empleadosperfiles = new EmpleadosPerfiles();
        Date date = new Date();

        empleadosperfiles.setIdempleado(idempleado);
        empleadosperfiles.setIdperfil(idperfil);
        empleadosperfiles.setIdempleadoalta(1);
        empleadosperfiles.setFechahoraalta(date);

        try {
            empleadosperfileService.save(empleadosperfiles);
        } catch (DataAccessException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("mensaje", "Error al agregar perfil");
            error.put("error", e.getMostSpecificCause().toString());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(true, HttpStatus.OK);
    }//addperfilusuario()

    /**
     *
     * @param idempleado
     * @param estatus
     * @return
     */
    @PostMapping("/cambiarestatususuario")
    public ResponseEntity<?> cambiarestatususuario(
            @RequestParam Long idempleado, @RequestParam byte estatus) {
        Map<String, Object> map = new HashMap<>();
        Usuarios usuario;
        try {
            usuario = usuarioService.findByIdEmpleado(idempleado);
            usuario.setEstatus(estatus);
            usuarioService.update(usuario, idempleado);
        } catch (DataAccessException e) {
            map.put("mensaje", "No se pudo cambiar el estatus del usuario");
            map.put("error", e.getMostSpecificCause());
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }// cambiarestatususuario()
}//-->
