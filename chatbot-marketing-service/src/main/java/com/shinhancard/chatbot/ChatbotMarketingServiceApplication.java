package com.shinhancard.chatbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.Data;

@SpringBootApplication
@Data
public class ChatbotMarketingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatbotMarketingServiceApplication.class, args);
	}
}
