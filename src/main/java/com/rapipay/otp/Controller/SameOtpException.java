package com.rapipay.otp.Controller;

public class SameOtpException extends RuntimeException {

	private String message;

	public SameOtpException() {

	}

	public SameOtpException(String message) {
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
