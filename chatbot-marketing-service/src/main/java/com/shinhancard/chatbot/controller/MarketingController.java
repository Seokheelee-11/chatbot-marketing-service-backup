package com.shinhancard.chatbot.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	private final MarketingManageRepository marketingManageRepository;

	@PostMapping("inquiry")
	public InquiryResponse inquiryMarketing(@RequestBody InquiryRequest getMarketingRequest) {
		log.info("Inquiry marketing start/n");
		ResultCode resultCode;
		List<MarketingInfo> marketingInfoes = new ArrayList<>();
		try {
			marketingInfoes = marketingService.inquiryMarketing(getMarketingRequest);
			resultCode = marketingInfoes.size() > 0 ? ResultCode.SUCCESS : ResultCode.FAILED;
		} catch (Exception e) {
			resultCode = ResultCode.FAILED;
		}
		
		return InquiryResponse.builder().resultCode(resultCode).marketingInfoes(marketingInfoes).build();
	}

	@PostMapping("apply")
	public ApplyResponse applyMarketing(@RequestBody ApplyRequest applyMarketingRequest) {
		return marketingService.applyMarketing(applyMarketingRequest);
	}

	
	// marketing Managing을 위한 화면 CRUD용
	@PostMapping
	public void saveMarketingManage(@RequestBody MarketingManage marketingManage) {
		marketingManageRepository.save(marketingManage);	
	}

	@GetMapping()
	public List<MarketingManage> getMarketinManageAll() {
		return marketingManageRepository.findAll();
	}

	@GetMapping("{id}")
	public MarketingManage getMarketingManage(@PathVariable String id) {
		return marketingManageRepository.findOneById(id);
	}

	@PutMapping("{id}")
	public void updateMarketingManage(@RequestBody MarketingManage marketingManage) {
		marketingManageRepository.save(marketingManage);
	}

	@DeleteMapping("{id}")
	public void deleteMarketingManage(@PathVariable String id) {
		marketingManageRepository.deleteById(id);
	}

}