/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cominvi.app.oauth.controllers;

import com.cominvi.app.commons.general.Empresas;
import com.cominvi.app.oauth.services.IEmpresasService;
import com.cominvi.app.oauth.services.IInicioService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author saul.granados
 */
@RestController
@RequestMapping("/inicio")
public class InicioCotroller {

    @Autowired
    private IInicioService inicioService;
    
    @Autowired
    private IEmpresasService empresaService;

    @GetMapping("/cargarMenuxPerfil/{idempleado}/{idempresa}/{idsistema}")
    public ResponseEntity<?> cargarMenuxPerfil(@PathVariable Long idempleado, @PathVariable Long idempresa, @PathVariable int idsistema) {

        Map<String, Object> m = new HashMap<>();
        try {
        	Empresas empresa = empresaService.findById(idempresa);
            m.put("menu", inicioService.cargarMenuxPerfil(idempleado, idempresa, idsistema));
            m.put("roles", inicioService.cargaRolesxPerfil(idempleado, idempresa, idsistema));
            m.put("empresa", empresa.getRazonsocial());
        } catch (DataAccessException e) {

            m.put("mensaje", "Error al consultar el listado de menu por perfil");
            m.put("error", e.getMostSpecificCause());
            return new ResponseEntity<Map<String, Object>>(m, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<Map<String, Object>>(m, HttpStatus.OK);
    }
    
    @GetMapping("/getMenuMultiplexPerfil/{idempleado}/{idempresa}/{idsistema}")
    public ResponseEntity<?> getMenuMultiplexPerfil(@PathVariable Long idempleado, @PathVariable Long idempresa, @PathVariable int idsistema) {

        Map<String, Object> m = new HashMap<>();
        try {
        	Empresas empresa = empresaService.findById(idempresa);
            m.put("menu", inicioService.getMenuMultiplexPerfil(idempleado, idempresa, idsistema));
            m.put("roles", inicioService.cargaRolesxPerfil(idempleado, idempresa, idsistema));
            m.put("empresa", empresa.getRazonsocial());
        } catch (DataAccessException e) {

            m.put("mensaje", "Error al consultar el listado de menu por perfil");
            m.put("error", e.getMostSpecificCause());
            return new ResponseEntity<Map<String, Object>>(m, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<Map<String, Object>>(m, HttpStatus.OK);
    }

     /**
     * @param idempleado
     * @return
     */
    @GetMapping("/cargaminasusuario/{idempleado}")
    public ResponseEntity<?> cargaminasusuario(@PathVariable Long idempleado) {
        List<Map<String, Object>> minas;
        try {
            minas = inicioService.cargaminasusuario(idempleado);
        } catch (DataAccessException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("mensaje", "Error");
            error.put("error", e.getMostSpecificCause().toString());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(minas, HttpStatus.OK);
    }// cargaminasusuario()

}// -->
