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


import com.cominvi.app.commons.general.Empresas;
import com.cominvi.app.oauth.services.IEmpresasService;

@RestController
@RequestMapping("/empresa")
public class EmpresasController {

  @Autowired private IEmpresasService empresaService;

  @GetMapping()
  public List<Empresas> index() {
    return empresaService.findAll();
  }

  @GetMapping("/{idempresa}")
  public Empresas buscarEmpresas(@PathVariable Long idempresa) {

    Empresas empresa = null;

    try {
      empresa = empresaService.findById(idempresa);
    } catch (DataAccessException e) {
      return null;
    }

    return empresa;
  }

  @PostMapping()
  public ResponseEntity<?> guardar(@RequestBody Empresas empresa) {

    Map<String, Object> map = new HashMap<>();
    try {
      empresa = empresaService.save(empresa);
    } catch (DataAccessException e) {
      map.put("mensaje", "Error al insertar el registro");
      map.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
      return new ResponseEntity<Map<String, Object>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return new ResponseEntity<Empresas>(empresa, HttpStatus.CREATED);
  }

  @PutMapping("/{idempresa}")
  public ResponseEntity<?> actualizar(@RequestBody Empresas empresa, @PathVariable Long idempresa) {

    Map<String, Object> map = new HashMap<>();
    try {
      empresaService.update(empresa, idempresa);
      empresa = empresaService.findById(idempresa);
    } catch (DataAccessException e) {
      map.put("mensaje", "Error al insertar el registro");
      map.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
      return new ResponseEntity<Map<String, Object>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return new ResponseEntity<Empresas>(empresa, HttpStatus.CREATED);
  }

  @DeleteMapping("/{idempresa}")
  public ResponseEntity<?> eliminarEmpresas(@PathVariable Long idempresa) {

    Map<String, Object> map = new HashMap<>();
    try {
      empresaService.removeById(idempresa);
    } catch (DataAccessException e) {
      map.put("mensaje", "Error al insertar el registro");
      map.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
      return new ResponseEntity<Map<String, Object>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    map.put("mensaje", "El registro se elimino con exito");
    return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
  }
  
  @GetMapping("/logos/{idempresa}")
  public Map<String, Object> logos(@PathVariable Long idempresa) {
	  return empresaService.logos(idempresa);
  }
  
}
