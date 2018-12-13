package com.project.paymentservice.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project.paymentservice.domain.Payment;
import com.project.paymentservice.domain.Payment.PaymentStatus;
import com.project.paymentservice.domain.Payment.PaymentType;
import com.project.paymentservice.repository.Payments;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PaymentProcessImpl implements PaymentProcess {
	
	private final Payments payments;

	public ResponseEntity<String> process(Payment payment) {
	payment.setStatus(PaymentStatus.APPROVED);
	Payment paymentSaved = payments.save(payment);

	if (payment.getType().equals(PaymentType.SLIP)) {
		return ResponseEntity.ok("1111111 1 2222222 2 333333 3 44444 4");
	} else {
		return ResponseEntity.ok(paymentSaved.getId().toString());
	}
}

}
