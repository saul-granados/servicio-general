package com.cominvi.app.oauth.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cominvi.app.commons.general.Centroscostousuario;
import com.cominvi.app.oauth.services.ICentroscostousuarioService;

@RestController
@RequestMapping("/centrocostousuario")
public class CentrocostoUsuarioController {

	@Autowired
	private ICentroscostousuarioService centrocostoService;
	
	@GetMapping("/{idcentroscostousuario}")
	public ResponseEntity<?> findById(@PathVariable Long idcentroscostousuario) {
		
		Centroscostousuario centrocosto;
		try {
			centrocosto = centrocostoService.findById(idcentroscostousuario);
		}catch (DataAccessException e) {
			Map<String, Object> m = new HashMap<>();
			m.put("mensaje", "No se pudo guardar la autorización");
            m.put("error", e.getMostSpecificCause());
            return new ResponseEntity<Map<String, Object>>(m, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<Centroscostousuario>(centrocosto, HttpStatus.OK);
	}
	
	@GetMapping("/findusuario/{idmodulo}/{idempleado}/{idempresa}")
	public ResponseEntity<?> findUsuario(@PathVariable Long idmodulo, @PathVariable Long idempleado, @PathVariable Long idempresa) {
		
		Centroscostousuario centroscostos;
		try {
			centroscostos = centrocostoService.findByIdmoduloAndIdempleado(idmodulo, idempleado, idempresa);
		}catch (DataAccessException e) {
			Map<String, Object> m = new HashMap<>();
			m.put("mensaje", "No se pudo guardar la autorización");
            m.put("error", e.getMostSpecificCause());
            return new ResponseEntity<Map<String, Object>>(m, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<Centroscostousuario>(centroscostos, HttpStatus.OK);
	}
	
}
