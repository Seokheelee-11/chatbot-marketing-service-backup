package com.shinhancard.chatbot.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RequestEAISkill {
	@Autowired
	private final RestTemplate restTemplate;
	
	

	public Map<String,Object> requestEAISkill(String eaiSchema, Map<String, Object> input){
		Map<String, Object> result = new HashMap<>();

		if ("CBS00029".equals(eaiSchema)) {
			String url= "http://localhost:7690/marketing/track";
			Map<String,Object> tempmap = new HashMap<>();
			tempmap.put("seq", 2);
			result = restTemplate.postForObject(url, tempmap, Map.class);
		}
		if ("CBS00030".equals(eaiSchema)) {
			String url= "http://localhost:7690/marketing/apply";
			Map<String,Object> tempmap = new HashMap<>();
			tempmap.put("seq", 1);
			result = restTemplate.postForObject(url, tempmap, Map.class);
		}

		return result;
	}
}
