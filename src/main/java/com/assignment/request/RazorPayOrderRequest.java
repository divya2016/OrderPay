package com.assignment.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RazorPayOrderRequest {
    private Integer amount;
    private String currency;
    private String receipt;
}
