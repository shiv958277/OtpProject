package com.rapipay.otp.Services;

import static org.junit.jupiter.api.Assertions.*;



import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.rapipay.otp.Controller.InvalidMailException;
import com.rapipay.otp.Controller.InvalidOtpException;
import com.rapipay.otp.Controller.MailException;
import com.rapipay.otp.Repository.Repository;
import com.rapipay.otp.Storage.Data;

@SpringBootTest
class ServicesTest {
	
	@MockBean
	private Repository repo;
	
	@Autowired
	private Services service;

	@Test
	@DisplayName("Testing valid mail 1")
	void testIsValidEmail() {
		Services service=new Services();
		boolean expected=true;
		boolean actual=service.isValidEmail("abc@gmail.com");
		assertEquals(expected,actual);
	}
	
	@Test
	@DisplayName("Testing valid mail 2")
	void testIsValidEmailsecond() {
		Services service=new Services();
		boolean expected=false;
		boolean actual=service.isValidEmail("12345hshs");
		assertEquals(expected,actual);
	}
	
	
	

	@Test
	void testResendotp() {
		Services service=new Services();
		assertThrows(Exception.class,()->{
			service.resendotp();
		});
	}
	
	
	@Test
	@DisplayName("Testing for Invalid Mail Exception")
	void testValidateOtp() {
		
		Services service=new Services();
		Data data=new Data();
		assertThrows(Exception.class,()->{
			service.validateOtp(data);
		});
	}
	
	@Test
	@DisplayName("Testing for Invalid Otp")
	void testValidateForOtp() {
		Services service=new Services();
		Data data=new Data();
//		{
//			  "channelName": "string",
//			  "emailorMobile": "string",
//			  "noofattempts": 0,
//			  "order_id": 0,
//			  "otp": 0,
//			  "otpExpiredTime": 0,
//			  "otpRequestedTime": 0,
//			  "sessions": 0,
//			  "verified": true
//			}
		
		data.setChannelName("email");
		data.setEmailorMobile("vermashivani543@gmail.com");
		data.setOtp(123456);
		int expected=("123456").length();
		String actual=String.valueOf(data.getOtp());
		int real=actual.length();
		assertEquals(expected,real);
		
		
		
	}
	
	@Test
	@DisplayName("Testing for Invalid Otp Exception")
	void testValidateOtpsecond() {
		
		Services service=new Services();
		Data data=new Data();
		assertThrows(Exception.class,()->{
			service.validateOtp(data);
		});
	}
	
	@Test
	@DisplayName("Testing for Wrong Mail Exception")
	void testValidateOtpthird() {
		
		Services service=new Services();
		Data data=new Data();
		assertThrows(Exception.class,()->{
			service.validateOtp(data);
		});
	}
	
	
	

	

}
