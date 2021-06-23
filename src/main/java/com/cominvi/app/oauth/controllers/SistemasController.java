package com.cominvi.app.oauth.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.cominvi.app.commons.general.Sistemas;
import com.cominvi.app.oauth.services.ISistemasService;

@RestController
@RequestMapping("/sistema")
public class SistemasController {

  @Autowired private ISistemasService sistemaService;

  @GetMapping()
  public List<Sistemas> index() {
    return sistemaService.findAll();
  }

  @GetMapping("/{idsistema}")
  public Sistemas buscarSistemas(@PathVariable Long idsistema) {

    Sistemas sistema = null;

    try {
      sistema = sistemaService.findById(idsistema);
    } catch (DataAccessException e) {
      return null;
    }

    return sistema;
  }

  @PostMapping()
  public ResponseEntity<?> guardar(@RequestBody Sistemas sistema) {

    Map<String, Object> map = new HashMap<>();
    try {
      sistema = sistemaService.save(sistema);
    } catch (DataAccessException e) {
      map.put("mensaje", "Error al insertar el registro");
      map.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
      return new ResponseEntity<Map<String, Object>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return new ResponseEntity<Sistemas>(sistema, HttpStatus.CREATED);
  }

  @PutMapping("/{idsistema}")
  public ResponseEntity<?> actualizar(@RequestBody Sistemas sistema, @PathVariable Long idsistema) {

    Map<String, Object> map = new HashMap<>();
    try {
      sistemaService.update(sistema, idsistema);
      sistema = sistemaService.findById(idsistema);
    } catch (DataAccessException e) {
      map.put("mensaje", "Error al insertar el registro");
      map.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
      return new ResponseEntity<Map<String, Object>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return new ResponseEntity<Sistemas>(sistema, HttpStatus.CREATED);
  }

  @DeleteMapping("/{idsistema}")
  public ResponseEntity<?> eliminarSistemas(@PathVariable Long idsistema) {

    Map<String, Object> map = new HashMap<>();
    try {
      sistemaService.removeById(idsistema);
    } catch (DataAccessException e) {
      map.put("mensaje", "Error al insertar el registro");
      map.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
      return new ResponseEntity<Map<String, Object>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    map.put("mensaje", "El registro se elimino con exito");
    return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
  }
}
