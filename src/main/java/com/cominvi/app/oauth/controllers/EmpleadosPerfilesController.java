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

import com.cominvi.app.oauth.services.IEmpleadosPerfilesService;
import com.cominvi.app.commons.general.EmpleadosPerfiles;

@RestController
@RequestMapping("/empleadosperfile")
public class EmpleadosPerfilesController {

  @Autowired private IEmpleadosPerfilesService empleadosperfileService;

  @GetMapping()
  public List<EmpleadosPerfiles> index() {
    return empleadosperfileService.findAll();
  }

  @GetMapping("/{idempleado}/{idperfil}")
  public EmpleadosPerfiles buscarEmpleadosPerfiles(
      @PathVariable Long idempleado, @PathVariable Long idperfil) {

    EmpleadosPerfiles empleadosperfile = null;

    try {
      empleadosperfile = empleadosperfileService.findById(idempleado, idperfil);
    } catch (DataAccessException e) {
      return null;
    }

    return empleadosperfile;
  }

  @PostMapping()
  public ResponseEntity<?> guardar(@RequestBody EmpleadosPerfiles empleadosperfile) {

    Map<String, Object> map = new HashMap<>();
    try {
      empleadosperfile = empleadosperfileService.save(empleadosperfile);
    } catch (DataAccessException e) {
      map.put("mensaje", "Error al insertar el registro");
      map.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
      return new ResponseEntity<Map<String, Object>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return new ResponseEntity<EmpleadosPerfiles>(empleadosperfile, HttpStatus.CREATED);
  }

  @PutMapping("/{idempleado}/{idperfil}")
  public ResponseEntity<?> actualizar(
      @RequestBody EmpleadosPerfiles empleadosperfile,
      @PathVariable Long idempleado,
      @PathVariable Long idperfil) {

    Map<String, Object> map = new HashMap<>();
    try {
      empleadosperfileService.update(empleadosperfile, idempleado, idperfil);
      empleadosperfile = empleadosperfileService.findById(idempleado, idperfil);
    } catch (DataAccessException e) {
      map.put("mensaje", "Error al insertar el registro");
      map.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
      return new ResponseEntity<Map<String, Object>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return new ResponseEntity<EmpleadosPerfiles>(empleadosperfile, HttpStatus.CREATED);
  }

  @DeleteMapping("/{idempleado}/{idperfil}")
  public ResponseEntity<?> eliminarEmpleadosPerfiles(
      @PathVariable Long idempleado, @PathVariable Long idperfil) {

    Map<String, Object> map = new HashMap<>();
    try {
      empleadosperfileService.removeById(idempleado, idperfil);
    } catch (DataAccessException e) {
      map.put("mensaje", "Error al insertar el registro");
      map.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
      return new ResponseEntity<Map<String, Object>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    map.put("mensaje", "El registro se elimino con exito");
    return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
  }
}
