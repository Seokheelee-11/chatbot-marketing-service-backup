package com.shinhancard.chatbot.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class ApplyRequest {
	//TODO:: 추후 채널 관련 기능 추가
//	private String channel;
	private String userId; // CLNN에 맵핑
	private String marketingId; // MO_N에 맵핑
	
}
