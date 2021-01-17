package com.assignment.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderRequest {
	private String name;
	private String email;
	private String mobileNumber;
	private Integer amount;
	private String currency;
	private String receipt;
}
