package com.rapipay.otp.Controller;

public class MailException extends Exception {
	private String message;

	public MailException() {

	}

	public MailException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
