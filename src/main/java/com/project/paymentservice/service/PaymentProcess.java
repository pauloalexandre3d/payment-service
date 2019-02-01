package com.project.paymentservice.service;

import com.project.paymentservice.domain.Payment;

public interface PaymentProcess {

	Payment process(Payment payment);

}
