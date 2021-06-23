/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cominvi.app.oauth.services;

import java.util.List;
import java.util.Map;

/**
 *
 * @author saul.granados
 */
public interface IInicioService {

    /**
     * Metodo que regresa el menu de acuerdo al perfil del usuario
     *
     * @param idempleado
     * @param idempresa
     * @return
     */
    List<Map<String, Object>> cargarMenuxPerfil(Long idempleado, Long idempresa, Integer idsistema);

    /**
     * Muestro los roles asignados de acuerdo al sistema y empresa que el
     * usuario esta logeado
     *
     * @param idempleado
     * @param idempresa
     * @param idsistema
     * @return
     */
    List<Map<String, Object>> cargaRolesxPerfil(Long idempleado, Long idempresa, Integer idsistema);

    /**
     * Muestro las minas que tiene asignados de acuerdo al idempleado esta
     * logeado
     *
     * @param idempleado
     * @return
     */
    List<Map<String, Object>> cargaminasusuario(Long idempleado);
    
    /**
     * Metodo que regresa el menu de acuerdo al perfil del usuario
     *
     * @param idempleado
     * @param idempresa
     * @return
     */
    List<Map<String, Object>> getMenuMultiplexPerfil(Long idempleado, Long idempresa, Integer idsistema);

}
