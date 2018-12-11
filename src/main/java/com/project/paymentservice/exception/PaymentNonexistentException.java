package com.project.paymentservice.exception;

public class PaymentNonexistentException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PaymentNonexistentException(String paymentId) {
		 super(String.format("Payment with Id  %s not exists.", paymentId));
	}
	
}
