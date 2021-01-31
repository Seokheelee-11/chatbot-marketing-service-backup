package com.shinhancard.chatbot.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.shinhancard.chatbot.dto.request.GetMarketingRequest;


@Component
public class EAISchemaMapper {
	
	public Map<String,Object> requestToSchema(GetMarketingRequest input){
		Map<String,Object> output = new HashMap<>();
		output.put("start", input.getStart());
		return output;
	}
}
