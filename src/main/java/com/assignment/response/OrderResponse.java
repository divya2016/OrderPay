package com.assignment.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderResponse {
	private String id;
	private String entity;
	private Integer amount;
	private Integer amount_paid;
	private Integer amount_due;
	private String currency;
	private String receipt;
	private String offer_id;
	private String status;
	private Integer attempts;
	private List<Notes> notes;
	private long created_at;

}
