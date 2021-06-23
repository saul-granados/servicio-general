package com.cominvi.app.oauth.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cominvi.app.commons.general.Modulos;
import com.cominvi.app.oauth.services.IModuloService;

@RestController
@RequestMapping("/modulo")
public class ModuloControlller {

	@Autowired
	private IModuloService moduloService;
	
	@GetMapping
	public List<Modulos> index() {
		return moduloService.findAll();
	}
	
}
