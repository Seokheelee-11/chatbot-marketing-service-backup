package com.shinhancard.chatbot.dto.response;

import com.shinhancard.chatbot.domain.ResultCode;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class ApplyResponse {
	
	private ResultCode resultCode;
	private String marketingName;
	private String responseMessage;
	
		
}
