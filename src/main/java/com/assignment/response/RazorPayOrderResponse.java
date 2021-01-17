package com.assignment.response;

import lombok.Data;

@Data
public class RazorPayOrderResponse {
	private String id;
	private String entity;
	private Integer amount;
	private Integer amount_paid;
	private Integer amount_due;
	private String currency;
	private String receipt;
	private String offer_id;
	private String status;
}
