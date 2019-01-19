package com.fcm.server.sendpushnotificationdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SendPushNotificationDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SendPushNotificationDemoApplication.class, args);
	}

}

