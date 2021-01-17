package com.assignment.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class ConfigConstant {
 
	@Value("${razorPay.url}")
	private String razorPayUrl;
	@Value("${razorPay.userName}")
	private String razorPayUserName;
	@Value("${razorPay.password}")
	private String razorPayPassword;
	@Value("${razorPay.create}")
	private String orderCreate;

}
