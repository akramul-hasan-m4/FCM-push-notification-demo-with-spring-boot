package com.fcm.server.sendpushnotificationdemo.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "title", "text", "click_action" })
public class Notification implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("title")
	private String title;
	
	@JsonProperty("text")
	private String text;
	
	@JsonProperty("click_action")
	private String clickAction;

	@JsonProperty("title")
	public String getTitle() {
		return title;
	}

	@JsonProperty("title")
	public void setTitle(String title) {
		this.title = title;
	}

	@JsonProperty("text")
	public String getText() {
		return text;
	}

	@JsonProperty("text")
	public void setText(String text) {
		this.text = text;
	}

	@JsonProperty("click_action")
	public String getClickAction() {
		return clickAction;
	}

	@JsonProperty("click_action")
	public void setClickAction(String clickAction) {
		this.clickAction = clickAction;
	}

	@Override
	public String toString() {
		return "Notification [title=" + title + ", text=" + text + ", clickAction=" + clickAction + "]";
	}
	
}
