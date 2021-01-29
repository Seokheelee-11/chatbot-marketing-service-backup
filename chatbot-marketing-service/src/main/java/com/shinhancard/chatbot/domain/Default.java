package com.shinhancard.chatbot.domain;

import java.time.LocalDateTime;

import lombok.Data;


@Data
public class Default {
	private String name;
	private Boolean status;
	private String discription;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	
}


