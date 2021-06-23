package com.cominvi.app.oauth.services;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cominvi.app.commons.general.Usuariosexternos;

public interface IUsuariosexternosService {

  List<Usuariosexternos> findAll();

  Usuariosexternos findById(Long idusuarioexterno);
  
  Usuariosexternos findByCorreo(String correo);
  
  Usuariosexternos findByCelular(String celular);
  
  Usuariosexternos findByCorreoAndCelular(String username);

  Usuariosexternos save(Usuariosexternos usuariosexterno);

  Boolean saveAll(List<Usuariosexternos> usuariosexternos);

  Usuariosexternos update(Usuariosexternos usuariosexterno, Long idusuarioexterno);

  Boolean removeById(Long idusuarioexterno);

  Usuariosexternos findOneCustom(String sql);

  List<Usuariosexternos> findAllCustom(String sql);
  
  int recuperarPassword(Usuariosexternos usuario, int registro);
  
  boolean enviarCorreo( String contenido, String destino, String asunto);
  
  String getParametro(Long idparametro);
  
  Map<String, Object> reestablecer(String correo, String clave, String password, int registro);
  
  Map<String, Object> activaByClave(String clave, String celular);
  
  Date agregaMinutos(int minutos);
  
  int DiffMunitos(Date fecha);
}
