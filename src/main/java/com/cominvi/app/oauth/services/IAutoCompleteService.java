package com.cominvi.app.oauth.services;

import java.util.List;
import java.util.Map;

public interface IAutoCompleteService {
	
	List<Map<String, Object>> autocompleteEmpresas(String term);

}
