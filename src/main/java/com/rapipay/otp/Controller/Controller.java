package com.rapipay.otp.Controller;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rapipay.otp.Services.Services;
import com.rapipay.otp.Storage.Data;

@RestController
public class Controller {

	private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);
	@Autowired(required = true)
	private Data data;

	@Autowired
	private Services notificationService;

	@RequestMapping(method = RequestMethod.POST, value = "generateOtp")
	public String generate(@RequestParam String emailorMobile, @RequestParam int order_id, @RequestParam String channel_name) throws MailException {
		LOGGER.info("for generate otp method");
		try {
			data.setEmailorMobile(emailorMobile); // Receiver's email address
			data.setOrder_id(order_id);
			data.setChannelName(channel_name);
			if (channel_name.equalsIgnoreCase("email")) {
				notificationService.generateotp(data);

			} else {
				notificationService.sendsms(data);
			}

			return "Otp sent successfullly";
		}

		catch (InvalidMailException e) {
			return e.getMessage();
		}
		catch(AttemptException e) {
			return e.getMessage();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "validateOtp")
	public String validateOtp(@RequestBody Data obj) {
		LOGGER.info("for validate otp");
		try {
			return notificationService.validateOtp(obj);
		} catch (MailException e) {
			// TODO Auto-generated catch block
			return e.getMessage();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "resendOtp")
	public String resendOtp() throws MailException {
		
		
		LOGGER.info("for resending otp");
		
		try {
		notificationService.resendotp();

		return "Otp sent successfullly";
		}
		
		catch(InvalidTimeException e) {
			return e.getMessage();
			
		}

	}

}
