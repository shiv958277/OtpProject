package com.rapipay.otp.Services;

import java.util.Date;

import java.util.Random;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.rapipay.otp.Controller.AttemptException;
import com.rapipay.otp.Controller.Controller;
import com.rapipay.otp.Controller.InvalidMailException;
import com.rapipay.otp.Controller.InvalidOtpException;
import com.rapipay.otp.Controller.InvalidTimeException;
import com.rapipay.otp.Controller.MailException;
import com.rapipay.otp.Controller.SameOtpException;
import com.rapipay.otp.Repository.Repository;
import com.rapipay.otp.Storage.Data;

import net.bytebuddy.utility.RandomString;

@Service
public class Services {
	private static final Logger LOGGER = LoggerFactory.getLogger(Services.class);

	@Autowired
	Repository repo;

	@Value("${NoOfAttempts}")
	private int count;

	@Value("${TimeLimit}")
	private int time;

	@Autowired
	JavaMailSender javaMailSender;

	@Autowired
	private Data data;

	@Autowired
	public void MailService(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public boolean isValidEmail(String email) {
		LOGGER.info("for validating email");

		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
				+ "A-Z]{2,7}$";

		Pattern pat = Pattern.compile(emailRegex);
		if (email == null)
			return false;
		return pat.matcher(email).matches();
	}

	public void generateotp(Data data) throws MailException {
		LOGGER.info("for generating otp");
		if (isValidEmail(data.getEmailorMobile()) == false) {
			throw new InvalidMailException("Invalid Email Id");
		}

		Random random = new Random();
		int otp = 100000 + random.nextInt(900000);

		Data store = repo.findById(data.getEmailorMobile()).orElse(null);
		if (store == null) {

			data.setOtp(otp);
			long currentTime = System.currentTimeMillis() + 1000;
			data.setOtpRequestedTime(currentTime);
			data.setOtpExpiredTime(currentTime + 60000);
			data.setNoofattempts(3);
			data.setSessions(System.currentTimeMillis() + 180000);
			data.setVerified(false);
			sendEmail(data, otp);
			repo.save(data);

		}

		else if (store.getNoofattempts() <= 0 && store.getSessions() > System.currentTimeMillis()) {
			throw new AttemptException("You have exceeded the number of attempts. Try after "
					+ ((store.getSessions() - System.currentTimeMillis()) / 1000) + " seconds");
		}

		else {
			sendEmail(data, otp);
			int res = ((store.getNoofattempts() - 1) < 0) ? 3 : (store.getNoofattempts() - 1);
			store.setNoofattempts(res);
			store.setOtp(otp);
			long currentTime = System.currentTimeMillis() + 1000;
			store.setOtpRequestedTime(currentTime);
			store.setOtpExpiredTime(System.currentTimeMillis() + 60000);
			store.setVerified(false);
			if (store.getSessions() < System.currentTimeMillis())
				store.setSessions(System.currentTimeMillis() + 180000);
			repo.save(store);

		}

	}

	public void sendEmail(Data data, int OTP) throws MailException {
		LOGGER.info("for sending mail");

		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(data.getEmailorMobile());
		mail.setSubject("ONE TIME PASSWORD");
//		System.out.println(data.getOtp()+"........."+OTP);
		mail.setText("Your otp is" + " " + OTP);
		javaMailSender.send(mail);

	}

	public String validateOtp(Data obj) throws MailException {
		LOGGER.info("for validating otp");

		String email = obj.getEmailorMobile();
		int otp = obj.getOtp();
		String o = String.valueOf(otp);

		Data data = repo.findById(email).orElse(null);

		String ans;
		System.out.println(data);
		if (isValidEmail(email) == false) {
			return new InvalidMailException("Invalid Email Id").toString();
		}

		else if (obj.getOtp() == 0 || o.length() != 6) {
			data.setVerified(false);

			return new InvalidOtpException("Entered otp is not valid").toString();

		}

		else if (data == null)
			throw new MailException("This email is not a registered Email");

		long time = System.currentTimeMillis();
		long diff = (time - data.getOtpRequestedTime()) / 1000;
		System.out.println("Difference is : " + (diff));

		if ((diff) > 60) {
			data.setVerified(false);
			ans = "Otp has expired generate new otp";

		}

		else if (data.getOtp() == otp) {
			data.setVerified(true);
			ans = "Your otp is correct";

		}

		else
			ans = "Your otp is not correct";
		data.setVerified(false);

		return ans;
	}

	public void sendsms(Data data) {
		LOGGER.info("for sending sms");
		Random random = new Random();
		int otp = 100000 + random.nextInt(900000);
		Data store = repo.findById(data.getEmailorMobile()).orElse(null);
		if (store == null) {

			data.setOtp(otp);
			long currentTime = System.currentTimeMillis() + 1000;
			data.setOtpRequestedTime(currentTime);
			data.setOtpExpiredTime(currentTime + 60000);
			data.setNoofattempts(3);
			data.setSessions(System.currentTimeMillis() + 180000);
			data.setVerified(false);
			repo.save(data);

		}
		
		else if (store.getNoofattempts() <= 0 && store.getSessions() > System.currentTimeMillis()) {
			throw new AttemptException("You have exceeded the number of attempts. Try after "
					+ ((store.getSessions() - System.currentTimeMillis()) / 1000) + " seconds");
		}
		
		
		
		else {
			System.out.println("Your otp is "+otp);
			int res = ((store.getNoofattempts() - 1) < 0) ? 3 : (store.getNoofattempts() - 1);
			store.setNoofattempts(res);
			store.setOtp(otp);
			long currentTime = System.currentTimeMillis() + 1000;
			store.setOtpRequestedTime(currentTime);
			store.setOtpExpiredTime(System.currentTimeMillis() + 60000);
			store.setVerified(false);
			if (store.getSessions() < System.currentTimeMillis())
				store.setSessions(System.currentTimeMillis() + 180000);
			repo.save(store);

		}
	
	}

	public void resendotp() throws MailException {
		LOGGER.info("for resending otp");
		System.out.println(data.getEmailorMobile());
		Data store = repo.findById(data.getEmailorMobile()).orElse(null);
		if ((store.getOtpRequestedTime() + 90000) > (System.currentTimeMillis())) {
			throw new InvalidTimeException("You can regenrate the otp only after 90 seconds");
		}

		if (store.getNoofattempts() <= 0 && store.getSessions() > System.currentTimeMillis()) {
			throw new AttemptException("You have exceeded the number of attempts. Try after "
					+ ((store.getSessions() - System.currentTimeMillis()) / 1000) + " seconds");
		}

		Random random = new Random();
		int otp = 100000 + random.nextInt(900000);

		data.setOtp(otp);
		if (otp != store.getOtp()) {

			long currentTime = System.currentTimeMillis() + 1000;
			data.setOtpRequestedTime(currentTime);
			data.setOtpExpiredTime(currentTime + 60000);
			int res = ((store.getNoofattempts() - 1) < 0) ? 3 : (store.getNoofattempts() - 1);
			data.setNoofattempts(res);
			data.setSessions(System.currentTimeMillis() + 180000);
			data.setVerified(false);
			sendEmail(data, otp);
			repo.save(data);
		}

		else {
			throw new SameOtpException("Previous and current Otp are same");
		}

	}

}
