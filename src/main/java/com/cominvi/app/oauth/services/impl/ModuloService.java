package com.cominvi.app.oauth.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cominvi.app.commons.general.Modulos;
import com.cominvi.app.oauth.repositories.JdbcModulosRepository;
import com.cominvi.app.oauth.services.IModuloService;

@Service
public class ModuloService implements IModuloService {

	@Autowired
	private JdbcModulosRepository moduloDao;
	
	@Transactional(readOnly = true)
	@Override
	public List<Modulos> findAll() {
		return moduloDao.findAll();
	}

}
