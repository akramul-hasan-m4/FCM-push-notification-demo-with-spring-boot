package com.fcm.server.sendpushnotificationdemo.model;



import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;


public class Data implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("extra_information")
	private String extraInformation;

	@JsonProperty("extra_information")
	public String getExtraInformation() {
		return extraInformation;
	}

	@JsonProperty("extra_information")
	public void setExtraInformation(String extraInformation) {
		this.extraInformation = extraInformation;
	}

	@Override
	public String toString() {
		return "Data [extraInformation=" + extraInformation + "]";
	}
	
}