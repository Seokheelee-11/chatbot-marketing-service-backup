package com.shinhancard.chatbot;

import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@EnableDiscoveryClient
@SpringBootApplication
public class ChatbotMarketingServiceApplication {

	public static void main(String[] args) {
//		SpringApplication.run(ChatbotMarketingServiceApplication.class, args);
		Properties properties = System.getProperties();
		properties.put("@APP_NAME", "chatbot-marketing-service");

		SpringApplication application = new SpringApplication(ChatbotMarketingServiceApplication.class);
		application.addListeners(new ApplicationPidFileWriter()); // pid 를 작성하는 역할을 하는 클래스 선언
		application.run(args);
	}
}
