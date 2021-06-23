package com.cominvi.app.oauth.services;

import java.util.List;
import java.util.Map;

import com.cominvi.app.commons.general.Empresas;

public interface IEmpresasService {

  List<Empresas> findAll();

  Empresas findById(Long idempresa);

  Empresas save(Empresas empresa);

  Boolean saveAll(List<Empresas> empresas);

  Empresas update(Empresas empresa, Long idempresa);

  Boolean removeById(Long idempresa);
  
  Map<String, Object> logos(Long idempresa);
}
