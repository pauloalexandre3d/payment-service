package com.project.paymentservice.service;

import org.springframework.http.ResponseEntity;

import com.project.paymentservice.domain.Payment;

public interface PaymentProcess {

	ResponseEntity<String> process(Payment payment);

}
