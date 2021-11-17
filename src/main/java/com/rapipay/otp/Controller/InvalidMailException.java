package com.rapipay.otp.Controller;

public class InvalidMailException extends RuntimeException{
	
	private String message;

	public InvalidMailException() {

	}

	public InvalidMailException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
