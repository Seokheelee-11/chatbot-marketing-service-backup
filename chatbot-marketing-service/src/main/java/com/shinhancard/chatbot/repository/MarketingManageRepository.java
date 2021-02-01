package com.shinhancard.chatbot.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.shinhancard.chatbot.entity.MarketingManage;


@Repository
public interface MarketingManageRepository extends MongoRepository<MarketingManage, String>{
	
	
	MarketingManage findOneByMarketingId(String marketingId);
	
}
