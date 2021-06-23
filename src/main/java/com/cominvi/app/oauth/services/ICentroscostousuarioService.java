package com.cominvi.app.oauth.services;

import java.util.List;
import com.cominvi.app.commons.general.Centroscostousuario;

public interface ICentroscostousuarioService {

  List<Centroscostousuario> findAll();

  Centroscostousuario findById(Long idcentroscostousuario);
  
  Centroscostousuario findByIdmoduloAndIdempleado(Long idmodulo, Long idempleado, Long idempresa);

  Centroscostousuario save(Centroscostousuario centroscostousuario);

  Boolean saveAll(List<Centroscostousuario> centroscostousuarios);

  Centroscostousuario update(Centroscostousuario centroscostousuario, Long idcentroscostousuario);

  Boolean removeById(Long idcentroscostousuario);

  Centroscostousuario findOneCustom(String sql);

  List<Centroscostousuario> findAllCustom(String sql);
}
