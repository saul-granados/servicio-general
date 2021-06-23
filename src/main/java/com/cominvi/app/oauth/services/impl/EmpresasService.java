package com.cominvi.app.oauth.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cominvi.app.commons.general.Empresas;
import com.cominvi.app.oauth.repositories.JdbcEmpresasRepository;
import com.cominvi.app.oauth.services.IEmpresasService;


@Service
public class EmpresasService implements IEmpresasService {
	
	@Autowired
	private JdbcEmpresasRepository empresaDao;

	@Transactional(readOnly = true)
	@Override
	public List<Empresas> findAll() {
		return empresaDao.findAll();
	}

	@Transactional(readOnly = true)
	@Override
	public Empresas findById(Long idempresa) {
		return empresaDao.findById(idempresa);
	}

	@Transactional
	@Override
	public Empresas save(Empresas empresa) {
		return empresaDao.save(empresa);
	}

	@Transactional
	@Override
	public Boolean saveAll(List<Empresas> empresas) {
		return empresaDao.saveAll(empresas);
	}

	@Transactional
	@Override
	public Empresas update(Empresas empresa, Long idempresa) {
		return empresaDao.update(empresa, idempresa);
	}

	@Transactional
	@Override
	public Boolean removeById(Long idempresa) {
		return empresaDao.removeById(idempresa);
	}

	@Override
	public Map<String, Object> logos(Long idempresa) {
		
		Map<String, Object> m = new HashMap<String, Object>();
		Empresas empresa = empresaDao.findById(idempresa);
		m.put("logofull", empresa.getLogofull());
		m.put("logomin", empresa.getLogomin());
		
		return m;
	}
}
