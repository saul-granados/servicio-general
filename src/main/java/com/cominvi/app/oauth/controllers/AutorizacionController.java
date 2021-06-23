package com.cominvi.app.oauth.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cominvi.app.commons.oauth.Autorizaciones;
import com.cominvi.app.oauth.services.IAutorizacionService;

@RestController
@RequestMapping("/autorizaciones")
public class AutorizacionController {

	@Autowired
	private IAutorizacionService autorizacionService;
	
	
	@PostMapping()
	public ResponseEntity<?> guardar(@RequestBody Autorizaciones autorizacion) {
		Map<String, Object> m = new HashMap<>();
		Autorizaciones autorizado;
		try {
			autorizado = autorizacionService.save(autorizacion);
			if(autorizado == null) {
				m.put("mensaje", "Usuario o contraseña invalidos");
				return new ResponseEntity<Map<String, Object>>(m, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}catch (DataAccessException e) {
            m.put("mensaje", "No se pudo guardar la autorización");
            m.put("error", e.getMostSpecificCause());
            return new ResponseEntity<Map<String, Object>>(m, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		 return new ResponseEntity<Autorizaciones>(autorizado, HttpStatus.OK);
	}
	
	
}
