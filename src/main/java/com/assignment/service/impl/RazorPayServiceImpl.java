package com.assignment.service.impl;

import com.assignment.constants.IntegrationStatus;
import com.assignment.request.RazorPayOrderRequest;
import java.time.Duration;
import java.time.Instant;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignment.config.ConfigConstant;
import com.assignment.constants.Constants;
import com.assignment.data.models.IntergrationDetails;
import com.assignment.request.OrderRequest;
import com.assignment.request.ServiceRequest;
import com.assignment.response.OrderResponse;
import com.assignment.response.ServiceResponse;
import com.assignment.service.RazorPayService;
import com.assignment.utils.CommonUtils;

import lombok.extern.log4j.Log4j2;

/**
 * Service to make razorpay api calls
 */

@Service
@Log4j2
public class RazorPayServiceImpl implements RazorPayService {

    @Autowired
    private ConfigConstant configConstant;

    @Autowired
    private CommonUtils commonUtils;

    @Override
    public ServiceResponse<OrderResponse> createOrder(ServiceRequest<OrderRequest> serviceRequest) {
        Instant startTime = Instant.now();
        OrderRequest orderRequest = serviceRequest.getData();
        log.info("Entering into [RazorPayServiceImpl -> createOrder]", orderRequest.getMobileNumber());
        String integrationId = initiateIntegration(orderRequest);
        RazorPayOrderRequest razorPayOrderRequest = new RazorPayOrderRequest(orderRequest.getAmount(), orderRequest.getCurrency(), orderRequest.getReceipt());
        String response = CommonUtils.postRequestMethod(
                configConstant.getRazorPayUrl() + configConstant.getOrderCreate(), razorPayOrderRequest, CommonUtils
                        .prepareAuthHeader(configConstant.getRazorPayUserName(), configConstant.getRazorPayPassword()),
                Constants.CONTENT_TYPE_JSON);
        long apiResponseTime = Duration.between(startTime, Instant.now()).toMillis();
        OrderResponse serviceResponse = CommonUtils.extractObjectFromString(response, OrderResponse.class);
        Map<String, Object> query = new HashMap<>();
        query.put("_id", integrationId);
        Map<String, Object> updates = new HashMap<>();
        updates.put("apiResponse", serviceResponse);
        updates.put("timeTaken", apiResponseTime);
        if (null != serviceResponse && serviceResponse.getStatus().equals("created")) {
            log.info("Successfully processed the integration", orderRequest.getMobileNumber());
            updates.put("status", IntegrationStatus.SUCCESS.getIntegrationStatus());
            commonUtils.findAndModify("integrationDetails", IntergrationDetails.class, updates, query);
            return new ServiceResponse<>(serviceResponse, Constants.SUCCESS, "Order Created Successfully.");
        }
        updates.put("status", IntegrationStatus.FAILURE.getIntegrationStatus());
        commonUtils.findAndModify("integrationDetails", IntergrationDetails.class, updates, query);
        log.error("Failed to process the integration", orderRequest.getMobileNumber());
        return new ServiceResponse<>(Constants.FAILURE, "Order Creation Failed.");
    }

    private String initiateIntegration(OrderRequest orderRequest) {
        IntergrationDetails details = new IntergrationDetails(orderRequest, null,
                IntegrationStatus.INITIATED.getIntegrationStatus(), configConstant.getRazorPayUrl() + configConstant.getOrderCreate(), 0);
        details = commonUtils.insert(details);
        return details.getId();
    }
}
