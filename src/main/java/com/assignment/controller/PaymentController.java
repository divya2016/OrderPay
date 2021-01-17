package com.assignment.controller;

import com.assignment.request.PaymentCaptureRequest;
import com.assignment.request.ServiceRequest;
import com.assignment.response.ServiceResponse;
import com.assignment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author divya rani
 */
@RestController
@RequestMapping("v1/payment/")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/capture")
    public ServiceResponse<String> capturePayment(@RequestBody ServiceRequest<PaymentCaptureRequest> serviceRequest) {
        return paymentService.capturePayment(serviceRequest);
    }


}
