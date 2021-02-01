package com.shinhancard.chatbot.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shinhancard.chatbot.dto.request.ApplyMarketingRequest;
import com.shinhancard.chatbot.dto.request.GetMarketingRequest;
import com.shinhancard.chatbot.dto.response.ApplyMarketingResponse;
import com.shinhancard.chatbot.dto.response.GetMarketingResponse;
import com.shinhancard.chatbot.entity.MarketingManage;
import com.shinhancard.chatbot.repository.MarketingManageRepository;
import com.shinhancard.chatbot.service.MarketingService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("marketing")
@RequiredArgsConstructor
public class MarketingController {

	private final MarketingService marketingService;
	private final MarketingManageRepository MarketingManageRepository;
	
	@PostMapping("get")
	public GetMarketingResponse getMarketing(@RequestBody GetMarketingRequest getMarketingRequest) {
		return marketingService.getMarketing(getMarketingRequest);
	}
	
	@PostMapping("manage")
	public void setMarketingManage(@RequestBody MarketingManage marketingManage) {
		MarketingManageRepository.save(marketingManage);
	}
	
	@PostMapping("apply")
	public ApplyMarketingResponse applyMarketing(@RequestBody ApplyMarketingRequest applyMarketingRequest) {
		return marketingService.applyMarketing(applyMarketingRequest);
	}
	
	
}