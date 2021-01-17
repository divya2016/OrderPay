package com.assignment.service;

import java.util.List;

import com.assignment.data.models.Orders;
import com.assignment.request.OrderRequest;
import com.assignment.request.ServiceRequest;
import com.assignment.response.OrderResponse;
import com.assignment.response.ServiceResponse;

public interface OrderService {

	ServiceResponse<OrderResponse> createOrder(ServiceRequest<OrderRequest> serviceRequest);

	ServiceResponse<List<Orders>> fetchAllUnpaidOrders();

	ServiceResponse<List<Orders>> fetchAllOrders();
}
