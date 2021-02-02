package com.shinhancard.chatbot.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shinhancard.chatbot.domain.MarketingInfo;
import com.shinhancard.chatbot.domain.ResultCode;
import com.shinhancard.chatbot.dto.request.ApplyRequest;
import com.shinhancard.chatbot.dto.request.InquiryRequest;
import com.shinhancard.chatbot.dto.response.ApplyResponse;
import com.shinhancard.chatbot.dto.response.InquiryResponse;
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
	public InquiryResponse getMarketing(@RequestBody InquiryRequest getMarketingRequest) {
		ResultCode resultCode;
		List<MarketingInfo> marketingInfoes = new ArrayList<>();
		try {
			marketingInfoes = marketingService.getMarketing(getMarketingRequest);
			resultCode = marketingInfoes.size() > 0 ? ResultCode.SUCCESS : ResultCode.FAILED;
		} catch (Exception e) {
			resultCode = ResultCode.FAILED;
		}
		return InquiryResponse.builder().resultCode(resultCode).marketingInfoes(marketingInfoes).build();
	}

	@PostMapping("manage")
	public void setMarketingManage(@RequestBody MarketingManage marketingManage) {
		MarketingManageRepository.save(marketingManage);
	}

	@PostMapping("apply")
	public ApplyResponse applyMarketing(@RequestBody ApplyRequest applyMarketingRequest) {
		
		
		return marketingService.applyMarketing(applyMarketingRequest);
	}

}