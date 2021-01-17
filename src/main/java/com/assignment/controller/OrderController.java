package com.assignment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.data.models.Orders;
import com.assignment.request.OrderRequest;
import com.assignment.request.ServiceRequest;
import com.assignment.response.OrderResponse;
import com.assignment.response.ServiceResponse;
import com.assignment.service.OrderService;

/**
 * @author divyarani
 *
 */
@RestController
@RequestMapping("v1/order/")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@PostMapping("create")
	public ServiceResponse<OrderResponse> createOrder(@RequestBody ServiceRequest<OrderRequest> serviceRequest) {
		return orderService.createOrder(serviceRequest);
	}
	
	@GetMapping("unpaid")
	public ServiceResponse<List<Orders>> fetchAllUnpaidOrders() {
		return orderService.fetchAllUnpaidOrders();
	}

	@GetMapping("all")
	public ServiceResponse<List<Orders>> fetchAllOrders() {
		return orderService.fetchAllOrders();
	}

}
