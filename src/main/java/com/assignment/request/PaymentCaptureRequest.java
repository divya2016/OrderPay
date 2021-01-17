package com.assignment.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentCaptureRequest {
    private String orderId;
    private String razorpay_signature;
    private String razorpay_order_id;
    private String razorpay_payment_id;
    private String code;
    private String description;
    private String source;
    private String step;
    private String reason;
    private String order_id;
    private String payment_id;
}
