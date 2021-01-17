package com.assignment.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ServiceResponse<T> {
	private T data;
	private String status;
	private String message;
	public ServiceResponse(String status, String message) {
		super();
		this.status = status;
		this.message = message;
	}
	
	
	
}
