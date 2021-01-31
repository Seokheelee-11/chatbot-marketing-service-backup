package com.shinhancard.chatbot.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shinhancard.chatbot.dto.request.GetMarketingRequest;
import com.shinhancard.chatbot.service.EAISchemaMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("marketing")
@RequiredArgsConstructor
public class MarketingController {

	private final EAISchemaMapper eAISchemaMapper;
	private final MarketingService marketingService;
	
	@PostMapping("get")
	public String getMarketing(@RequestBody GetMarketingRequest input) {
		Map<String,Object> test = eAISchemaMapper.requestToSchema(input); 
		
		
		//결과 return
		String result ="";
		return result;
	}
	
	
	
	
}