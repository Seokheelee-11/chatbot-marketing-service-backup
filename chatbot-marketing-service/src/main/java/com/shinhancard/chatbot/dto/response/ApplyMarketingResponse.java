package com.shinhancard.chatbot.dto.response;

import com.shinhancard.chatbot.domain.ResultCode;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApplyMarketingResponse {
	
	private ResultCode resultCode;
	private String marketingName;
	private String responseMessage;
	
		
}
