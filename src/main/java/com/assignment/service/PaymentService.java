package com.assignment.service;

import com.assignment.request.PaymentCaptureRequest;
import com.assignment.request.ServiceRequest;
import com.assignment.response.ServiceResponse;

public interface PaymentService {
    ServiceResponse<String> capturePayment(ServiceRequest<PaymentCaptureRequest> serviceRequest);
}
