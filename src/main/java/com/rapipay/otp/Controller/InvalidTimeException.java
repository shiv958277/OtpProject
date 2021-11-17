package com.rapipay.otp.Controller;

public class InvalidTimeException extends RuntimeException {
	private String message;

	public InvalidTimeException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public InvalidTimeException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return message;
	}
	
}
