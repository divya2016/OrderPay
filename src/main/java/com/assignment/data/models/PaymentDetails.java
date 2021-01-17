package com.assignment.data.models;

import com.assignment.request.PaymentCaptureRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentDetails extends AuditEntity{
	private static final long serialVersionUID = 1L;
	private String paymentId;
	private String status;
	PaymentCaptureRequest rawPaymentResponse;
}
