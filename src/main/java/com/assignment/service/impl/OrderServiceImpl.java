package com.assignment.service.impl;

import com.assignment.constants.Constants;
import com.assignment.data.models.Orders;
import com.assignment.request.OrderRequest;
import com.assignment.request.ServiceRequest;
import com.assignment.response.OrderResponse;
import com.assignment.response.ServiceResponse;
import com.assignment.service.OrderService;
import com.assignment.service.RazorPayService;
import com.assignment.service.helper.OrderServiceHelper;
import com.assignment.utils.CommonUtils;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service to create and fetch order
 */

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderServiceHelper orderServiceHelper;
	@Autowired
	private CommonUtils commounUtils;

	@Autowired
	private RazorPayService razorPayService;

	@Override
	public ServiceResponse<OrderResponse> createOrder(ServiceRequest<OrderRequest> serviceRequest) {
		ServiceResponse<OrderResponse> serviceResponse = orderServiceHelper.validateOrderRequest(serviceRequest);
		if (serviceResponse.getStatus().equals(Constants.SUCCESS)) {
			serviceResponse = razorPayService.createOrder(serviceRequest);
			if (serviceResponse.getStatus().equals(Constants.SUCCESS)) {
				orderServiceHelper.saveData(serviceResponse, serviceRequest);
			}
		}
		return serviceResponse;
	}

	@Override
	public ServiceResponse<List<Orders>> fetchAllUnpaidOrders() {
		List<Orders> orders = commounUtils.findAllUnpaidOrders(Orders.class);
		return new ServiceResponse<>(orders, Constants.SUCCESS, "data fetch successfully.");
	}

	@Override
	public ServiceResponse<List<Orders>> fetchAllOrders() {
		List<Orders> orders = commounUtils.findAll(Orders.class);
		return new ServiceResponse<>(orders, Constants.SUCCESS, "data fetch successfully.");
	}

}
