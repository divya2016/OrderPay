package com.assignment.data.models;

import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Order tracker entity, maintains all teh stages an order goes through
 */

@Document("orderTracker")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Orders extends AuditEntity {

	private static final long serialVersionUID = 1L;
	private String name;
	private String mobileNumber;
	private String email;
	private String orderId;
	private Integer amount;
	private Integer amountDue;
	private String status;
	private ArrayList<PaymentDetails> paymentDetails;

	public Orders(String orderId, Integer amount, Integer amountDue,
				  String status,String mobileNumber,String email, String name) {
		this.orderId = orderId;
		this.amount = amount;
		this.amountDue = amountDue;
		this.status = status;
		this.name = name;
		this.email = email;
		this.mobileNumber = mobileNumber;
	}

}
