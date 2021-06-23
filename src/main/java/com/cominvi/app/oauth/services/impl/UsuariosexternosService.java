package com.cominvi.app.oauth.services.impl;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cominvi.app.commons.general.Usuariosexternos;
import com.cominvi.app.oauth.models.SmsResponse;
import com.cominvi.app.oauth.repositories.GlobalRowMapper;
import com.cominvi.app.oauth.repositories.JdbcUsuariosexternosRepository;
import com.cominvi.app.oauth.services.IAESEncryptionDecryption;
import com.cominvi.app.oauth.services.IUsuariosexternosService;
import com.cominvi.app.oauth.utils.RandomString;
import com.cominvi.app.oauth.utils.Utils;

@Service
public class UsuariosexternosService implements IUsuariosexternosService {

	private Logger log = LoggerFactory.getLogger(UsuariosexternosService.class);

	@Autowired
	private JdbcUsuariosexternosRepository usuariosexternoDao;

	@Autowired
	private GlobalRowMapper globalDao;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private IAESEncryptionDecryption claveService;

	@Autowired
	private SmsService smsService;

	@Transactional(readOnly = true)
	@Override
	public List<Usuariosexternos> findAll() {
		return usuariosexternoDao.findAll();
	}

	@Transactional(readOnly = true)
	@Override
	public Usuariosexternos findById(Long idusuarioexterno) {
		return usuariosexternoDao.findById(idusuarioexterno);
	}

	@Transactional(readOnly = true)
	@Override
	public Usuariosexternos findByCorreo(String correo) {
		return findOneCustom("select * from general.dbo.usuariosexternos where correo = '" + correo + "' ");
	}

	@Transactional(readOnly = true)
	@Override
	public Usuariosexternos findByCelular(String celular) {
		return findOneCustom("select * from general.dbo.usuariosexternos where celular = '" + celular + "' ");
	}

	@Transactional(readOnly = true)
	@Override
	public Usuariosexternos findByCorreoAndCelular(String username) {
		return findOneCustom("select * from general.dbo.usuariosexternos where correo = '" + username
				+ "' or celular like '%" + username + "' ");
	}

	@Transactional
	@Override
	public Usuariosexternos save(Usuariosexternos usuariosexterno) {
		usuariosexterno.setPassword(passwordEncoder.encode(usuariosexterno.getPassword()));
		return usuariosexternoDao.save(usuariosexterno);
	}

	@Transactional
	@Override
	public Boolean saveAll(List<Usuariosexternos> usuariosexternos) {
		return usuariosexternoDao.saveAll(usuariosexternos);
	}

	@Transactional
	@Override
	public Usuariosexternos update(Usuariosexternos usuariosexterno, Long idusuarioexterno) {
		return usuariosexternoDao.update(usuariosexterno, idusuarioexterno);
	}

	@Transactional
	@Override
	public Boolean removeById(Long idusuarioexterno) {
		return usuariosexternoDao.removeById(idusuarioexterno);
	}

	@Transactional(readOnly = true)
	@Override
	public Usuariosexternos findOneCustom(String sql) {

		try {
			return (Usuariosexternos) usuariosexternoDao.regresaObjeto(sql,
					new JdbcUsuariosexternosRepository.UsuariosexternosRowMapper());
		} catch (DataAccessException e) {
			log.warn("El usuario no existe");
			return null;
		}

	}

	@Transactional(readOnly = true)
	@Override
	public List<Usuariosexternos> findAllCustom(String sql) {
		return usuariosexternoDao.regresaLista(sql, new JdbcUsuariosexternosRepository.UsuariosexternosRowMapper());
	}

	@Transactional
	@Override
	public int recuperarPassword(Usuariosexternos usuario, int registro) {
		Date fechaactivacion = null;
		String clave = "", encode_clave = "", encodedCorreo = "";
		int minutos = (registro == 1) ? 60 : 15;
		fechaactivacion = agregaMinutos(minutos);

		// No se pudo generar la fecha de activación
		if (fechaactivacion == null) {
			return 100;
		}

		if (registro == 1) {
			// Generamos código para enviar a celular
			RandomString gen = new RandomString(5);
			clave = gen.nextString().toUpperCase();
			// Enviamos el mensaje de texto
			SmsResponse sms = smsService.enviar(usuario.getCelular(),"[CoMinVi] Tu codigo de restablecimiento de contraseña es " + clave);
			if (sms == null || (sms.getStatus().getCode() == 0 || sms.getStatus().getCode() > 3)) {
				return 400;
			}

			usuario.setClaveactivacion(clave);
		} else {
			// Genramos url para enviar por correo
			clave = generarRandomText();
			encode_clave = passwordEncoder.encode(clave);
			encodedCorreo = Base64.getEncoder().encodeToString(usuario.getCorreo().getBytes());
			fechaactivacion = agregaMinutos(15);
			usuario.setClaveactivacion(claveService.encrypt(clave, Utils.KEY_ACTIVACION));
		}

		usuario.setFechavencimientoclave(fechaactivacion);
		usuario.setReestablecerpassword(1);
		usuariosexternoDao.update(usuario, usuario.getIdusuarioexterno());

		if (registro == 2) {
			// Enviamos correo
			String activacion = getParametro((long) 24) + "?correo=" + encodedCorreo + "&clave=" + encode_clave;
			String plantilla = Utils.regresaCuerpoEmailRecuperaPassword(usuario.getCorreo(), activacion);
			enviarCorreo(plantilla, usuario.getCorreo(), "Solicitud de restablecimiento de contraseña");
		}

		return 200;
	}

	public String generarRandomText() {
		SecureRandom random = new SecureRandom();
		String clave = new BigInteger(130, random).toString(32);
		return clave;
	}

	@Transactional(readOnly = true)
	@Override
	public boolean enviarCorreo(String contenido, String destino, String asunto) {

		EmailService email = new EmailService();
		String sql = "select nombre, valor from general.dbo.parametros where idparametro in (4,5,6,7) order by idparametro asc";
		List<Map<String, Object>> parametros = globalDao.regresaLista(sql, new GlobalRowMapper.GRowMapper());

		String origen = parametros.get(2).get("valor").toString();

		email.setProperties(parametros.get(0).get("valor").toString(), parametros.get(1).get("valor").toString(),
				origen, parametros.get(3).get("valor").toString());

		return email.sendEmail(origen, destino, null, asunto, contenido, false, null);
	}

	@Transactional(readOnly = true)
	@Override
	public String getParametro(Long idparametro) {
		String sql = "select * from general.dbo.parametros where idparametro = " + idparametro + "";
		Map<String, Object> parametro = (Map<String, Object>) globalDao.regresaObjeto(sql,
				new GlobalRowMapper.GRowMapper());
		return parametro.get("valor").toString();
	}

	@Transactional
	@Override
	public Map<String, Object> reestablecer(String username, String clave, String password, int registro) {

		Map<String, Object> response = new HashMap<>();
		Usuariosexternos usuario = findByCorreo(username);

		if (registro == 1) {
			usuario = findByCelular("+52" + username);
		} else {
			usuario = findByCorreo(username);
		}

		if (usuario == null) {
			response.put("codigo", 100);
			response.put("mensaje", "La información ingresada no es la correcta");
			return response;
		}

		int minutos = DiffMunitos(usuario.getFechavencimientoclave());
		if ((minutos > 60 && registro == 1) || (minutos > 15 && registro == 2)) {
			response.put("codigo", 100);
			response.put("mensaje",
					"Se agoto el tiempo para poder reestablecer la contraseña, vuelva a solicitar el proceso");
			return response;
		}

		if (registro == 1) {
			if (!usuario.getClaveactivacion().equals(clave)) {
				response.put("codigo", 100);
				response.put("mensaje", "El código de reestablecimiento de contraseña no es correcto");
				return response;
			}
		} else {
			String codigo = claveService.decrypt(usuario.getClaveactivacion(), Utils.KEY_ACTIVACION);
			if (!passwordEncoder.matches(codigo, clave)) {
				response.put("codigo", 100);
				response.put("mensaje", "El código de reestablecimiento de contraseña no es correcto");
				return response;
			}
		}

		if (passwordEncoder.matches(password, usuario.getPassword())) {
			response.put("codigo", 100);
			response.put("mensaje", "La contraseña ingresada no puede ser igual a la anterior");
			return response;
		}

		usuario.setPassword(passwordEncoder.encode(password));
		usuario.setClaveactivacion("");
		usuario.setFechavencimientoclave(null);
		usuario.setReestablecerpassword(0);
		usuariosexternoDao.update(usuario, usuario.getIdusuarioexterno());

		response.put("codigo", 200);
		response.put("mensaje", "Se reestablecio la contraseña correctamente");

		return response;
	}

	@Transactional()
	@Override
	public Map<String, Object> activaByClave(String clave, String celular) {
		Map<String, Object> map = new HashMap<>();
		Usuariosexternos usuario = findByCelular(celular);

		if (usuario == null) {
			map.put("codigo", 100);
			map.put("mensaje", "El número de celular ingresado no se encuentra registrado");
			return map;
		}

		if (usuario.getClaveactivacion().trim().equals("") || !usuario.getClaveactivacion().equals(clave)) {
			map.put("codigo", 100);
			map.put("mensaje", "El código ingresado no es valido");
			return map;
		}

		int minutos = DiffMunitos(usuario.getFechavencimientoclave());
		if (minutos > 60) {
			map.put("codigo", 100);
			map.put("mensaje",
					"Se agoto el tiempo para poder activar tu cuenta, por seguridad vuelve a generar otro código");
			return map;
		}

		usuario.setClaveactivacion("");
		usuario.setFechavencimientoclave(null);
		usuario.setEstatus(1);
		update(usuario, usuario.getIdusuarioexterno());

		map.put("codigo", 20);
		map.put("mensaje", "Se activo la cuenta con éxito, te invitamos a ingresar a la plataforma");
		return map;
	}

	@Transactional(readOnly = true)
	@Override
	public Date agregaMinutos(int minutos) {
		Map<String, Object> fecha = (Map<String, Object>) globalDao.regresaObjeto(
				"select dateadd(MINUTE, " + minutos + ", getdate()) as fecha", new GlobalRowMapper.GRowMapper());
		return Utils.StringToDate(fecha.get("fecha").toString());
	}

	@Transactional(readOnly = true)
	@Override
	public int DiffMunitos(Date fecha) {
		String strfecha = Utils.DateToString(fecha);
		String sql = "SELECT datediff(MINUTE, '" + strfecha + "', getdate()) as diferencia";
		Map<String, Object> diff = (Map<String, Object>) globalDao.regresaObjeto(sql, new GlobalRowMapper.GRowMapper());
		return Integer.parseInt(diff.get("diferencia").toString());
	}

}
