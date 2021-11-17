package com.rapipay.otp.Storage;

import java.util.Date;


import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.stereotype.Component;

@Entity
@Table(name = "Details")
@Component
public class Data {

	@Id
	private String emailorMobile;
	private int Otp;
	private long otpRequestedTime;
	private long otpExpiredTime;
	private int order_id;
	private int Noofattempts;
	private String channelName;
	private long sessions;
	private boolean verified;
	
	
	public Data(String emailorMobile, int otp, long otpRequestedTime, long otpExpiredTime, int order_id,
			int noofattempts, String channelName, long sessions, boolean verified) {
		super();
		this.emailorMobile = emailorMobile;
		this.Otp = otp;
		this.otpRequestedTime = otpRequestedTime;
		this.otpExpiredTime = otpExpiredTime;
		this.order_id = order_id;
		this.Noofattempts = noofattempts;
		this.channelName = channelName;
		this.sessions = sessions;
		this.verified = verified;
	}

	public Data() {
	}

	
	public String getEmailorMobile() {
		return emailorMobile;
	}


	public void setEmailorMobile(String emailorMobile) {
		this.emailorMobile = emailorMobile;
	}


	public long getSessions() {
		return sessions;
	}


	public void setSessions(long sessions) {
		this.sessions = sessions;
	}


	public boolean isVerified() {
		return verified;
	}


	public void setVerified(boolean verified) {
		this.verified = verified;
	}


	public long getOtpExpiredTime() {
		return otpExpiredTime;
	}

	public int getNoofattempts() {
		return Noofattempts;
	}

	

	public void setNoofattempts(int noofattempts) {
		Noofattempts = noofattempts;
	}

	

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public void setOtpExpiredTime(long otpExpiredTime) {
		this.otpExpiredTime = otpExpiredTime;
	}

	public int getOrder_id() {
		return order_id;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	

	public int getOtp() {
		return Otp;
	}

	public void setOtp(int otp) {
		Otp = otp;
	}

	public long getOtpRequestedTime() {
		return otpRequestedTime;
	}

	public void setOtpRequestedTime(long otpRequestedTime) {
		this.otpRequestedTime = otpRequestedTime;
	}

}
