package com.shinhancard.chatbot.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class EAISkillService {
	private final RestTemplate restTemplate;
	
	@Value("${chatbot.skill-service-name}")
	String url;
	
	public Map<String,Object> callEAISkill(String eaiSchema, Map<String, Object> input){

		Map<String,Object> tempmap = new HashMap<>();
		if ("CBS00029".equals(eaiSchema)) {
			url= "http://localhost:7690/marketing/track";
			tempmap.put("seq", 2);
		}
		if ("CBS00030".equals(eaiSchema)) {
			url= "http://localhost:7690/marketing/apply";
			tempmap.put("seq", 1);
		}
		return restTemplate.postForObject(url, tempmap, Map.class);
	}

}
