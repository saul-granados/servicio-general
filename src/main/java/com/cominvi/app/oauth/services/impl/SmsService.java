package com.cominvi.app.oauth.services.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cominvi.app.oauth.models.SmsResponse;
import com.cominvi.app.oauth.repositories.GlobalRowMapper;

@Service
public class SmsService {

	 @Autowired
	 private GlobalRowMapper globalDao;
	 
	 @Autowired
	 private RestTemplate clienteRest;
	 
	 
	 public SmsResponse enviar( String celular, String mensaje ) {
		 
		 try {
			 String url = getParamentro(29);
			 String token = getParamentro(30);
			 
			 // Creamos los headers
			 HttpHeaders headers = new HttpHeaders();
			 headers.setContentType(MediaType.APPLICATION_JSON);
			 headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
			 headers.add("Authorization", "Bearer " + token);
			 
			 Map<String, Object> map = new HashMap<>();
			 map.put("From", "CoMinVi");
			 map.put("To", celular);
			 map.put("Text", mensaje);
			 
			 HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);
			 
			 ResponseEntity<SmsResponse> response = clienteRest.exchange(url, HttpMethod.POST, entity, SmsResponse.class);
			 if (response.getStatusCode() == HttpStatus.OK) {
				 return response.getBody();
			 }
			 
			 return null;
		 }catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		 
	 }
	 
	 private String getParamentro( long idparametro ) {
		 String sql = "SELECT valor from general.dbo.parametros where idparametro = "+idparametro+" ";
		 Map<String, Object> map = (Map<String, Object>) globalDao.regresaObjeto(sql, new GlobalRowMapper.GRowMapper());
		 return map.get("valor").toString();
	 }
	
}
