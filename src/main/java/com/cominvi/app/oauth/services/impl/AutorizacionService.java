package com.cominvi.app.oauth.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cominvi.app.commons.general.Usuarios;
import com.cominvi.app.commons.oauth.Autorizaciones;
import com.cominvi.app.oauth.repositories.JdbcAutorizacionesRepository;
import com.cominvi.app.oauth.services.IAutorizacionService;
import com.cominvi.app.oauth.services.IUsuarioService;

@Service
public class AutorizacionService implements IAutorizacionService {

	@Autowired
	private JdbcAutorizacionesRepository autorizacionDao;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Override
	public Autorizaciones save(Autorizaciones autorizacion) {
		
		Usuarios usuario = usuarioService.validaUsuario(autorizacion.getUsuario(), autorizacion.getPassword());
		if(usuario == null) {
			return null;
		}
		autorizacion.setIdempleado(usuario.getIdempleado());
		return autorizacionDao.save(autorizacion);
	}

}
