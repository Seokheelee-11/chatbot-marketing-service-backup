package com.shinhancard.chatbot.domain;

public enum ResultCode {
	SUCCESS("00", "성공"),
	FAILED("99", "failed");
	
	
	private String resultCode;
	private String resultMessage;

	
	private ResultCode(String resultCode, String resultMessage) {
		this.resultCode	= resultCode;
		this.resultMessage = resultMessage;
	}
	
	public String getResultCode() {
		return resultCode;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public Boolean isSuccess() {
		if (this.equals(SUCCESS)) {
			return true;
		} else {
			return false;
		}
	}
}