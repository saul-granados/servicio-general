package com.cominvi.app.oauth.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


public class SmsResponse implements Serializable {

	@JsonProperty("ID")
	private String ID;
	@JsonProperty("From")
	private String From;
	@JsonProperty("To")
	private String To;
	@JsonProperty("Status")
	private Status Status;
	@JsonProperty("Cost")
	private Cost Cost;
	@JsonProperty("CreationTS")
	private String CreationTS;
	@JsonProperty("Text")
	private String Text;
	@JsonProperty("SmsCount")
	private int SmsCount;

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getFrom() {
		return From;
	}

	public void setFrom(String from) {
		From = from;
	}

	public String getTo() {
		return To;
	}

	public void setTo(String to) {
		To = to;
	}

	public Status getStatus() {
		return Status;
	}

	public void setStatus(Status status) {
		Status = status;
	}

	public Cost getCost() {
		return Cost;
	}

	public void setCost(Cost cost) {
		Cost = cost;
	}

	public String getText() {
		return Text;
	}

	public void setText(String text) {
		Text = text;
	}

	public int getSmsCount() {
		return SmsCount;
	}

	public void setSmsCount(int smsCount) {
		SmsCount = smsCount;
	}
	

	public String getCreationTS() {
		return CreationTS;
	}

	public void setCreationTS(String creationTS) {
		CreationTS = creationTS;
	}



	private static final long serialVersionUID = 1L;
}
