package com.cominvi.app.oauth.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Cost implements Serializable {

	@JsonProperty("Value")
	private double Value;
	@JsonProperty("Currency")
	private String Currency;

	public double getValue() {
		return Value;
	}

	public void setValue(double value) {
		Value = value;
	}

	public String getCurrency() {
		return Currency;
	}

	public void setCurrency(String currency) {
		Currency = currency;
	}

	private static final long serialVersionUID = 1L;

}
