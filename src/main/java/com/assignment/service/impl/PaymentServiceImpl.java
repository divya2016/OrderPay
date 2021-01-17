package com.assignment.service.impl;

import com.assignment.config.ConfigConstant;
import com.assignment.constants.Constants;
import com.assignment.constants.OrderStatus;
import com.assignment.constants.PaymentStatus;
import com.assignment.data.models.Orders;
import com.assignment.data.models.PaymentDetails;
import com.assignment.request.PaymentCaptureRequest;
import com.assignment.request.ServiceRequest;
import com.assignment.response.ServiceResponse;
import com.assignment.service.PaymentService;
import com.assignment.utils.CommonUtils;
import com.assignment.utils.SignatureUtil;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service to capture payment and update it to order tracker
 */

@Service
@Log4j2
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private ConfigConstant configConstant;

    @Autowired
    private CommonUtils commonUtils;

    /**
     * Validate and update payment
     *
     * @param serviceRequest
     * @return
     */

    @Override
    public ServiceResponse<String> capturePayment(ServiceRequest<PaymentCaptureRequest> serviceRequest) {
        PaymentCaptureRequest request = serviceRequest.getData();
        log.info("Entering into [PaymentServiceImpl -> capturePayment]", request.getOrderId());
        PaymentDetails paymentDetails = new PaymentDetails();
        paymentDetails.setRawPaymentResponse(request);
        Map<String, Object> query = new HashMap<>();
        query.put("orderId", request.getOrderId());

        Map<String, Object> updates = new HashMap<>();
        try {
            if (isValidSignature(request)) {
                log.info("Signature matched, marking payment as success", request.getOrderId());
                paymentDetails.setPaymentId(request.getRazorpay_payment_id());
                paymentDetails.setStatus(PaymentStatus.CAPTURED.getStatus());
                updates.put("paymentDetails", paymentDetails);
                updates.put("status", OrderStatus.PAID.getStatus());
                updates.put("amountDue", 0);
                commonUtils.findAndModify("orderTracker", Orders.class, updates, query);
                return new ServiceResponse<>(null, Constants.SUCCESS, "Payment captured successfully");

            } else {
                log.info("Signature mismatch found, marking payment as failed", request.getOrderId());
                paymentDetails.setPaymentId(request.getPayment_id());
                paymentDetails.setStatus(PaymentStatus.FAILED.getStatus());
                updates.put("paymentDetails", paymentDetails);
                updates.put("status", OrderStatus.FAILED.getStatus());
                commonUtils.findAndModify("orderTracker", Orders.class, updates, query);
                return new ServiceResponse<>(null, Constants.FAILURE, "Signature matching failed");
            }
        } catch (SignatureException ex) {
            log.error("Error occurred while generating signature", request.getOrderId(), ex);
            return new ServiceResponse<>(null, Constants.FAILURE, "Error occurred validating the signature");
        }
    }

    /**
     * Validate signature obtained from Razorpay
     *
     * @param request
     * @return
     * @throws SignatureException
     */
    private boolean isValidSignature(PaymentCaptureRequest request) throws SignatureException {
        log.info("Entering into [PaymentServiceImpl -> isValidSignature]", request.getOrderId());
        if (request.getRazorpay_order_id() == null) {
            log.info("payment failed as there is no orderId present in response", request.getOrderId());
            return false;
        }
        String data = request.getOrderId() + "|" + request.getRazorpay_payment_id();
        String generateSignature = SignatureUtil.calculateRFC2104HMAC(data, configConstant.getRazorPayPassword());
        return generateSignature != null
                && generateSignature.equals(request.getRazorpay_signature());

    }
}
