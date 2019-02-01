package com.project.paymentservice.service;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.any;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.paymentservice.domain.Buyer;
import com.project.paymentservice.domain.Client;
import com.project.paymentservice.domain.CreditCard;
import com.project.paymentservice.domain.Payment;
import com.project.paymentservice.domain.Payment.PaymentStatus;
import com.project.paymentservice.domain.Payment.PaymentType;
import com.project.paymentservice.repository.Payments;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class PaymentProcessTest {
	
	@Mock
	Payments payments;
	
	@InjectMocks
	PaymentProcessImpl paymentProcess;

	@Test
	public void testShouldVerifySlipPaymentProcess() {
		Payment dummyPayment = getDummyPayment();
		dummyPayment.setCreditCard(null);
		when(payments.save(any(Payment.class))).thenReturn(dummyPayment);
		assertThat(paymentProcess.process(dummyPayment).getBarcode(), equalTo("1111111 1 2222222 2 333333 3 44444 4"));
	}
	
	@Test
	public void testShouldVerifyCreditCardPaymentProcess() {
		Payment dummyPayment = getDummyPayment();
		dummyPayment.setType(PaymentType.CREDIT_CARD);
		when(payments.save(any(Payment.class))).thenReturn(dummyPayment);
		assertThat(paymentProcess.process(dummyPayment), equalTo(dummyPayment));
	}
	
	private Payment getDummyPayment() {
		Client client = new Client("123456789");
		Buyer buyer = new Buyer("paulo machado", "paulomachado@project.com", "46434925034");
		CreditCard creditCard = new CreditCard("paulo machado", "5131 7789 0356 3981", "08/2022", "736");
		Payment payment = new Payment(new ObjectId(), 100.1, Payment.PaymentType.SLIP, client, buyer, creditCard,
				PaymentStatus.APPROVED, "");
		return payment;
	}

}
