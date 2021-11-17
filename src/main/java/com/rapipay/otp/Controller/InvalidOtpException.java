package com.rapipay.otp.Controller;

public class InvalidOtpException extends RuntimeException {
	private String message;

	public InvalidOtpException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public InvalidOtpException(String message) {
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
