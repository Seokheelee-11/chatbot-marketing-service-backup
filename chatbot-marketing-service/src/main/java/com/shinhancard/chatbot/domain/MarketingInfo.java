package com.shinhancard.chatbot.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarketingInfo {

	private String marketingName;
	private String marketingId;
	private String description;
}
