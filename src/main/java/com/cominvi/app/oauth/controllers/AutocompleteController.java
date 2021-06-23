package com.cominvi.app.oauth.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cominvi.app.oauth.services.IAutoCompleteService;

@RestController
@RequestMapping("/autocomplete")
public class AutocompleteController {
	
	@Autowired
    private IAutoCompleteService autocompleteService;
	
	@GetMapping("/autocompleteEmpresas/{term}")
    public List<Map<String, Object>> autocompleteEmpresas(@PathVariable String term) {
        return autocompleteService.autocompleteEmpresas(term);
    }

}
