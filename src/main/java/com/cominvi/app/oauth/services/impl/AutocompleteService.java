package com.cominvi.app.oauth.services.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cominvi.app.oauth.repositories.GlobalRowMapper;
import com.cominvi.app.oauth.services.IAutoCompleteService;

@Service
public class AutocompleteService implements IAutoCompleteService {
	
	@Autowired
    private GlobalRowMapper globalDao;
	
	/**
     * @param idempresa
     * @param term
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public List<Map<String, Object>> autocompleteEmpresas(String term) {
        try {
            String sql = "SELECT idempresa as id, '' AS clave, razonsocial as nombre "
                    + " FROM general.dbo.empresas "
                    + " WHERE razonsocial LIKE '%" + term + "%';";

            return globalDao.regresaLista(sql, new GlobalRowMapper.GRowMapper());
        } catch (Exception e) {
            System.out.println("Error al obtener el autocomplte: " + e);
            return null;
        }
    }// autocompleteEmpresas()
	
}
