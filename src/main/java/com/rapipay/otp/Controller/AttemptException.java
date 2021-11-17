package com.rapipay.otp.Controller;

public class AttemptException extends RuntimeException{

	private String message;

	public AttemptException() {

	}

	public AttemptException(String message) {
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
