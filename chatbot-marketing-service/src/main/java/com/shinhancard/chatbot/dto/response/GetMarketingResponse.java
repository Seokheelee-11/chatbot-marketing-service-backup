package com.shinhancard.chatbot.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.shinhancard.chatbot.domain.MarketingInfo;
import com.shinhancard.chatbot.domain.ResultCode;

import lombok.Data;


@Data
public class GetMarketingResponse {
	
	private ResultCode resultCode;
	private Integer size;
	private List<MarketingInfo> marketingInfoes = new ArrayList<>();
	
	public void addMarketingInfo(MarketingInfo marketingInfo) {
		this.marketingInfoes.add(marketingInfo);
	}
	
	public void setSize() {
		this.size = this.marketingInfoes.size();
	}
	
	public void setResultCode() {
		if(this.size>0) {
			this.resultCode = ResultCode.SUCCESS;
		}else {
			this.resultCode = ResultCode.FAILED;
		}
	}
}
