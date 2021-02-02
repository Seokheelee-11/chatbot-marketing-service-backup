package com.shinhancard.chatbot.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.shinhancard.chatbot.domain.MarketingInfo;
import com.shinhancard.chatbot.domain.ResultCode;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;


@Getter
@Builder
@ToString
public class InquiryResponse {
	
	private ResultCode resultCode;
	private List<MarketingInfo> marketingInfoes = new ArrayList<>();
	

}
