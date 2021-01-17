package com.assignment.service.helper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.assignment.constants.Constants;
import com.assignment.data.models.Orders;
import com.assignment.request.OrderRequest;
import com.assignment.request.ServiceRequest;
import com.assignment.response.OrderResponse;
import com.assignment.response.ServiceResponse;
import com.assignment.utils.CommonUtils;

@Component
public class OrderServiceHelper {

	@Autowired
	private CommonUtils commonUtils;

	public ServiceResponse<OrderResponse> validateOrderRequest(ServiceRequest<OrderRequest> serviceRequest) {
		if (StringUtils.isEmpty(serviceRequest.getData().getMobileNumber())) {
			return new ServiceResponse<>(Constants.FAILURE, "mobile number can not be empty.");
		}
		if (!validateMobile(serviceRequest.getData().getMobileNumber())) {
			return new ServiceResponse<>(Constants.FAILURE, "phone number not valid.");
		}
		if (StringUtils.isEmpty(serviceRequest.getData().getName())) {
			return new ServiceResponse<>(Constants.FAILURE, "name can not be empty.");
		}
		if (!validateName(serviceRequest.getData().getName())) {
			return new ServiceResponse<>(Constants.FAILURE, "name not valid.");
		}
		if (StringUtils.isNotEmpty(serviceRequest.getData().getEmail())
				&& !validateEmail(serviceRequest.getData().getEmail())) {
			return new ServiceResponse<>(Constants.FAILURE, "email not valid.");
		}
		if (null == serviceRequest.getData().getAmount()) {
			return new ServiceResponse<>(Constants.FAILURE, "amount can not be empty.");
		}
		if (null == serviceRequest.getData().getAmount()) {
			return new ServiceResponse<>(Constants.FAILURE, "amount can not be empty.");
		}
		return new ServiceResponse<>(null, Constants.SUCCESS, null);
	}

	public void saveData(ServiceResponse<OrderResponse> serviceResponse, ServiceRequest<OrderRequest> orderRequest) {
		Orders order = new Orders(serviceResponse.getData().getId(), serviceResponse.getData().getAmount(),
				serviceResponse.getData().getAmount_due(), serviceResponse.getData().getStatus(),
				orderRequest.getData().getMobileNumber(), orderRequest.getData().getEmail(),
				orderRequest.getData().getName());
		commonUtils.save(order);
	}

	/**
	 * This method will validate email
	 * 
	 * @param email
	 * @return
	 */
	public static Boolean validateEmail(String email) {
		return email.matches(Constants.EMAIL_REGEX);
	}

	/**
	 * this method will validate mobile
	 * 
	 * @param mobile
	 * @return
	 */
	public static Boolean validateMobile(String mobile) {
		return mobile.matches(Constants.MOBILE_REGEX);
	}

	/**
	 * This method will validate mobile
	 * 
	 * @param name
	 * @return
	 */
	public static Boolean validateName(String name) {
		return name.matches(Constants.NAME_REGEX);
	}
}
