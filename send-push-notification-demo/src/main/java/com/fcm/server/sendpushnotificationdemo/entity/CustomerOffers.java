package com.fcm.server.sendpushnotificationdemo.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CustomerOffers implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer offerId;
	
	private String topicChanel;
	
	private String notificationTitle;
	
	private String notificationBody;
	
	private String clikActionActivity;
	
	private String extraInfo;
	
	private LocalDate offerPublisedDate;
	
	private Boolean isNewOffer;

	public Integer getOfferId() {
		return offerId;
	}

	public void setOfferId(Integer offerId) {
		this.offerId = offerId;
	}

	public String getTopicChanel() {
		return topicChanel;
	}

	public void setTopicChanel(String topicChanel) {
		this.topicChanel = topicChanel;
	}

	public String getNotificationTitle() {
		return notificationTitle;
	}

	public void setNotificationTitle(String notificationTitle) {
		this.notificationTitle = notificationTitle;
	}

	public String getNotificationBody() {
		return notificationBody;
	}

	public void setNotificationBody(String notificationBody) {
		this.notificationBody = notificationBody;
	}

	public String getClikActionActivity() {
		return clikActionActivity;
	}

	public void setClikActionActivity(String clikActionActivity) {
		this.clikActionActivity = clikActionActivity;
	}

	public String getExtraInfo() {
		return extraInfo;
	}

	public void setExtraInfo(String extraInfo) {
		this.extraInfo = extraInfo;
	}

	public LocalDate getOfferPublisedDate() {
		return offerPublisedDate;
	}

	public void setOfferPublisedDate(LocalDate offerPublisedDate) {
		this.offerPublisedDate = offerPublisedDate;
	}

	public Boolean getIsNewOffer() {
		return isNewOffer;
	}

	public void setIsNewOffer(Boolean isNewOffer) {
		this.isNewOffer = isNewOffer;
	}
	
}
