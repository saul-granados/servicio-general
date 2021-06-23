package com.cominvi.app.oauth.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Status implements Serializable {

	@JsonProperty("Code")
	private int Code;
	@JsonProperty("Name")
	private String Name;
	@JsonProperty("Description")
	private String Description;

	public int getCode() {
		return Code;
	}

	public void setCode(int code) {
		Code = code;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	private static final long serialVersionUID = 1L;

}
