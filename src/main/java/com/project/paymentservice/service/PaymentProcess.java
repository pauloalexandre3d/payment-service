package com.project.paymentservice.service;

import com.project.paymentservice.domain.Payment;

public interface PaymentProcess {

	String process(Payment payment);

}
