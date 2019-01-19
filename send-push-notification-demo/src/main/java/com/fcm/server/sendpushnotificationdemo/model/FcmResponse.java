package com.fcm.server.sendpushnotificationdemo.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FcmResponse implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("message_id")
	private String messageId;

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	@Override
	public String toString() {
		return "FcmResponse [messageId=" + messageId + "]";
	}
	
}
