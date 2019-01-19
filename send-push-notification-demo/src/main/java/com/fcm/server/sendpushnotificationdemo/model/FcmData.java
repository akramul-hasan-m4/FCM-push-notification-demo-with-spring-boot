package com.fcm.server.sendpushnotificationdemo.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"to",
"data",
"notification"
})
public class FcmData implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("to")
	private String to;
	
	@JsonProperty("data")
	private Data data;
	
	@JsonProperty("notification")
	private Notification notification;
	

	@JsonProperty("to")
	public String getTo() {
	return to;
	}

	@JsonProperty("to")
	public void setTo(String to) {
	this.to = to;
	}

	@JsonProperty("data")
	public Data getData() {
	return data;
	}

	@JsonProperty("data")
	public void setData(Data data) {
	this.data = data;
	}

	@JsonProperty("notification")
	public Notification getNotification() {
	return notification;
	}

	@JsonProperty("notification")
	public void setNotification(Notification notification) {
	this.notification = notification;
	}

	@Override
	public String toString() {
		return "FcmData [to=" + to + ", data=" + data + ", notification=" + notification + "]";
	}

}
