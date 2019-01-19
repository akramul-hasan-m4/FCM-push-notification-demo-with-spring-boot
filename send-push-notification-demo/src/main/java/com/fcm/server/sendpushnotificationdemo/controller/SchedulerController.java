package com.fcm.server.sendpushnotificationdemo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fcm.server.sendpushnotificationdemo.entity.CustomerOffers;
import com.fcm.server.sendpushnotificationdemo.model.Data;
import com.fcm.server.sendpushnotificationdemo.model.FcmData;
import com.fcm.server.sendpushnotificationdemo.model.FcmResponse;
import com.fcm.server.sendpushnotificationdemo.model.Notification;
import com.fcm.server.sendpushnotificationdemo.repo.CustomerOffersRepo;

@Component
public class SchedulerController {
	
	private static final String MILI_SECOND = "This mili second comes from properties file";
	private static final Logger LOGGER = LoggerFactory.getLogger(SchedulerController.class);
	
	@Value("${android.fcm.key}") private String androidFcmKey;
	@Value("${android.Fcm.Url}") private String androidFcmUrl;
	
	RestTemplate restTemplate = new RestTemplate();
	HttpHeaders httpHeaders = new HttpHeaders();
	
	@Autowired private CustomerOffersRepo dataRepo;

	@Scheduled(cron = "0 15 10 15 * ?")
	public void scheduleTaskUsingCronExpression() {
		//this scheduling a task to be executed at 10:15 AM on the 15th day of every month
		//see more corn https://docs.oracle.com/cd/E12058_01/doc/doc.1014/e12030/cron_expressions.htm
		//see more corn https://docs.spring.io/spring/docs/3.0.x/javadoc-api/org/springframework/scheduling/support/CronSequenceGenerator.html

		long now = System.currentTimeMillis() / 1000;
		LOGGER.info("schedule tasks using cron jobs -{}", now);
	}
	
	@Scheduled(cron = " 0 01 00 ? * *")
	private void sendNotificationEveryDay() {
		// this scheduling a task to be executed at 12:01 AM on the everyday
		List<CustomerOffers> data = dataRepo.findAll().stream().filter(c -> c.getIsNewOffer() == Boolean.TRUE).collect(Collectors.toList());
		data.stream().forEach(d -> {
			FcmData fcmData = show(d);
			
			httpHeaders.set("Authorization", "key=" + androidFcmKey);
			httpHeaders.set("Content-Type", "application/json");
			
			HttpEntity<FcmData> httpEntity = new HttpEntity<>(fcmData,httpHeaders);
			FcmData response = restTemplate.postForObject(androidFcmUrl,httpEntity,FcmData.class);
			LOGGER.info(response.toString());
			});
		
	}
	
	@Scheduled(cron = "0 0 */1 * * *")
	private void sendNotificationEveryhour() {
		// this scheduling a task to be executed at every hour
		
		List<CustomerOffers> data = dataRepo.findAll().stream().filter(c -> c.getIsNewOffer() == Boolean.TRUE).collect(Collectors.toList());
		data.stream().forEach(d -> {
			FcmData fcmData = show(d);
			
			httpHeaders.set("Authorization", "key=" + androidFcmKey);
			httpHeaders.set("Content-Type", "application/json");
			
			HttpEntity<FcmData> httpEntity = new HttpEntity<>(fcmData,httpHeaders);
			
			ParameterizedTypeReference<FcmResponse> typeRef = new ParameterizedTypeReference<FcmResponse>() {};
			
			ResponseEntity<FcmResponse> rs = restTemplate.exchange(androidFcmUrl, HttpMethod.POST, httpEntity, typeRef);
			if(rs.getStatusCode() == HttpStatus.OK) {
				d.setIsNewOffer(Boolean.FALSE);
				dataRepo.save(d);
			}
			LOGGER.info(rs.getStatusCode().toString());
			LOGGER.info(rs.getBody().toString());
			LOGGER.info(rs.getHeaders().toString());
		});
		
	}
	
	//@Scheduled(fixedDelayString = "${fixedDelay.in.milliseconds}")
	public void schedulePropExample() {
		LOGGER.info(MILI_SECOND);
	}
	
	protected static FcmData show(CustomerOffers offers) {
		Data data = new Data();
		data.setExtraInformation(offers.getExtraInfo());
		
		Notification noti = new Notification();
		noti.setTitle(offers.getNotificationTitle());
		noti.setText(offers.getNotificationBody());
		noti.setClickAction(offers.getClikActionActivity());
		
		FcmData fcm = new FcmData();
		fcm.setTo("/topics/"+offers.getTopicChanel());
		fcm.setData(data);
		fcm.setNotification(noti);
		
		return fcm;
	}
	
}
