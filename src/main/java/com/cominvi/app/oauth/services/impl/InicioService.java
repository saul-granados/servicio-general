/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cominvi.app.oauth.services.impl;

import com.cominvi.app.oauth.repositories.GlobalRowMapper;
import com.cominvi.app.oauth.services.IInicioService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author saul.granados
 */
@Service
public class InicioService implements IInicioService {

    @Autowired
    private GlobalRowMapper dao;

    @Override
    public List<Map<String, Object>> cargarMenuxPerfil(Long idempleado, Long idempresa, Integer idsistema) {
        String sql = "select distinct mp.idmenu idmenupadre, mp.nombre nombrepadre, mp.icono iconopadre, "
                + "m.idmenu, m.url, m.nombre, m.icono "
                + "from general.dbo.menu m inner join general.dbo.menu mp on m.idmenupadre = mp.idmenu "
                + "inner join general.dbo.menu_perfiles mper on m.idmenu = mper.idmenu "
                + "inner join general.dbo.perfiles p on mper.idperfil = p.idperfil "
                + "inner join general.dbo.empleados_perfiles e on p.idperfil = e.idperfil "
                + "where m.estatus = 1 and m.idmenupadre > 0 and e.idempleado = " + idempleado + " and p.idempresa = " + idempresa + " and p.idsistema = " + idsistema + " "
                + "group by GROUPING sets((mp.idmenu, mp.nombre, mp.icono, m.idmenu, m.url, m.url, m.nombre, m.icono), "
                + "(mp.idmenu, mp.nombre, mp.icono)) " 
                + "order by mp.idmenu, m.idmenu";

        return dao.regresaLista(sql, new GlobalRowMapper.GRowMapper());
    }

    @Override
    public List<Map<String, Object>> cargaRolesxPerfil(Long idempleado, Long idempresa, Integer idsistema) {
        String sql = "select r.descripcion from general.dbo.roles r "
                + "inner join general.dbo.perfiles p on r.idrol = p.idrol "
                + "inner join general.dbo.empleados_perfiles ep on p.idperfil = ep.idperfil "
                + "where ep.idempleado = " + idempleado + " and p.idempresa = " + idempresa + " and p.idsistema = " + idsistema + "";
        return dao.regresaLista(sql, new GlobalRowMapper.GRowMapper());
    }

    @Override
    public List<Map<String, Object>> cargaminasusuario(Long idempleado) {
        String sql = "SELECT m.idmina, m.nombremina AS mina"
                + " FROM operacion.dbo.minas m"
                + " WHERE m.estatus = 1;";
        return dao.regresaLista(sql, new GlobalRowMapper.GRowMapper());
    }// cargaminasusuario()

	@Override
	public List<Map<String, Object>> getMenuMultiplexPerfil(Long idempleado, Long idempresa, Integer idsistema) {
		String sql = "select distinct mp.idmenu idmenupadre, mp.nombre nombrepadre, mp.icono iconopadre,  "
                + "m.idmenu, m.url, m.nombre, m.icono "
                + "from general.dbo.menu m inner join general.dbo.menu mp on m.idmenupadre = mp.idmenu "
                + "inner join general.dbo.menu_perfiles mper on m.idmenu = mper.idmenu "
                + "inner join general.dbo.perfiles p on mper.idperfil = p.idperfil "
                + "inner join general.dbo.empleados_perfiles e on p.idperfil = e.idperfil "
                + "where m.estatus = 1 and m.idmenupadre > 0 and e.idempleado = " + idempleado + " and p.idempresa = " + idempresa + " and p.idsistema = " + idsistema + " "
                + "order by mp.idmenu, m.idmenu";

        return dao.regresaLista(sql, new GlobalRowMapper.GRowMapper());
	}

}
