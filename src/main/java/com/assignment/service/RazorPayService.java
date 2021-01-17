package com.assignment.service;

import com.assignment.request.OrderRequest;
import com.assignment.request.ServiceRequest;
import com.assignment.response.OrderResponse;
import com.assignment.response.ServiceResponse;

public interface RazorPayService {

	ServiceResponse<OrderResponse> createOrder(ServiceRequest<OrderRequest> serviceRequest);
}
