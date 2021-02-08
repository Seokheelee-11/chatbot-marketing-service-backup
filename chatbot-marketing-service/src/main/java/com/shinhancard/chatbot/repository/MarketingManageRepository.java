package com.shinhancard.chatbot.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.shinhancard.chatbot.entity.MarketingManage;


@Repository
public interface MarketingManageRepository extends MongoRepository<MarketingManage, String>{
	
	
	List<MarketingManage> findAll();
	
	MarketingManage findOneByMarketingId(String marketingId);
	MarketingManage findOneById(String id);
	
	
	

	
	
	
}
