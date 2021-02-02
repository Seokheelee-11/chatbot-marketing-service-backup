package com.shinhancard.chatbot.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class InquiryRequest {

	private Integer start;
	private Integer size;
	private String channel;
	private String userId; // CLNN에 맵핑
	private String targetChannel; // MO_BJ_TCD에 맵핑
	private Boolean showsApplied; // RG_OFF_INC_F에 맵핑 True -> Y, False -> N
}
