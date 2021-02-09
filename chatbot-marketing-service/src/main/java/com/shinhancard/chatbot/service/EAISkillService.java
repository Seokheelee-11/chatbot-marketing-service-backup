package com.shinhancard.chatbot.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
@RequiredArgsConstructor
public class EAISkillService {
	
	private final RestTemplate restTemplate;
	
	@Value("${chatbot.skill-eai-service-name}")
	private String domain;
	private String commonPath = "/skills/eai";

	public Map<String,Object> callEAISkill(String eaiSchema, Map<String, Object> input){
		log.info("call eai-skill start");
		String path = "";
		if ("CBS00029".equals(eaiSchema)) {
			path = "/CBS00029";			
		}
		if ("CBS00030".equals(eaiSchema)) {
			path = "/CBS00030";
		}
		
		StringBuffer sbUrl = new StringBuffer();
		sbUrl.append("http://").append(domain).append(commonPath).append(path);		
		String url = sbUrl.toString();
		
		log.info("url : {}\ninput : {}", url,input);
		Map<String,Object> result = restTemplate.postForObject(url, input, Map.class);
		log.info("call eai-skill result : {}", result);
		return result;
	}

}

