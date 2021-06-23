package com.cominvi.app.oauth.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cominvi.app.commons.general.Sistemas;
import com.cominvi.app.oauth.repositories.JdbcSistemasRepository;
import com.cominvi.app.oauth.services.ISistemasService;

@Service
public class SistemasService implements ISistemasService {
  @Autowired private JdbcSistemasRepository sistemaDao;

  @Transactional(readOnly = true)
  @Override
  public List<Sistemas> findAll() {
    return sistemaDao.findAll();
  }

  @Transactional(readOnly = true)
  @Override
  public Sistemas findById(Long idsistema) {
    return sistemaDao.findById(idsistema);
  }

  @Transactional
  @Override
  public Sistemas save(Sistemas sistema) {
    return sistemaDao.save(sistema);
  }

  @Transactional
  @Override
  public Boolean saveAll(List<Sistemas> sistemas) {
    return sistemaDao.saveAll(sistemas);
  }

  @Transactional
  @Override
  public Sistemas update(Sistemas sistema, Long idsistema) {
    return sistemaDao.update(sistema, idsistema);
  }

  @Transactional
  @Override
  public Boolean removeById(Long idsistema) {
    return sistemaDao.removeById(idsistema);
  }
}
