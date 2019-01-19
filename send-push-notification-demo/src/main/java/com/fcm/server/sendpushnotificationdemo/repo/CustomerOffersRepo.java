package com.fcm.server.sendpushnotificationdemo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fcm.server.sendpushnotificationdemo.entity.CustomerOffers;

public interface CustomerOffersRepo extends JpaRepository<CustomerOffers, Integer>{

}
