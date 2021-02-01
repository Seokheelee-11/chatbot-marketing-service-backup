package com.shinhancard.chatbot.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;

import lombok.Data;


@Data
public class MarketingManage {

	@Id
	private String id;
	
	private Boolean enabled;
	
	private String marketingId;
	private String marketingName;
	
	private String discription;
	
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	
	private String successMessage;
	private String failureMessage;
	
	private List<String> channels;
	private List<String> offers;

	public Boolean canApply() {
		Boolean result;
		result = (canDateApply() && isEnabled());
		return result;
	}
	
	public Boolean canDateApply() {
		LocalDateTime nowTime = LocalDateTime.now();
		if(this.startDate.isBefore(nowTime) && this.endDate.isAfter(nowTime)) {
			return true;
		}
		return false;
	}
	
	public Boolean isEnabled() {
		return this.enabled == null ? false:this.enabled;
	}

	

}
