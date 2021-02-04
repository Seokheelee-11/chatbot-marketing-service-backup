package com.shinhancard.chatbot.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class EAISkillService {
	private final RestTemplate restTemplate;
	
	@Value("${chatbot.skill-eai-service-name}")
	private String url;

	public Map<String,Object> callEAISkill(String eaiSchema, Map<String, Object> input){
		String path = "";
		if ("CBS00029".equals(eaiSchema)) {
			path = "/eai/schema/CBS00029";			
		}
		if ("CBS00030".equals(eaiSchema)) {
			path = "/eai/schema/CBS00030";
		}
		url = url + path;
		return restTemplate.postForObject(url, input, Map.class);
	}

}

