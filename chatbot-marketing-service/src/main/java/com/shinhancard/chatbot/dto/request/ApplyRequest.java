package com.shinhancard.chatbot.dto.request;

import lombok.Getter;

@Getter
public class ApplyRequest {

	private String channel;
	private String userId; // CLNN에 맵핑
	private String marketingId; // MO_N에 맵핑
	
}
