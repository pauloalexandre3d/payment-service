package com.project.paymentservice.interfaces;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.project.paymentservice.domain.Buyer;
import com.project.paymentservice.domain.Client;
import com.project.paymentservice.domain.CreditCard;
import com.project.paymentservice.domain.Payment;
import com.project.paymentservice.domain.Payment.PaymentStatus;
import com.project.paymentservice.exception.PaymentNonexistentException;
import com.project.paymentservice.interfaces.PaymentController;
import com.project.paymentservice.repository.Payments;
import com.project.paymentservice.service.PaymentProcess;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class PaymentControllerTest {

	@InjectMocks
	PaymentController paymentRest;

	@Mock
	PaymentProcess paymentProcess;

	@Mock
	Payments payments;

	@Captor
	ArgumentCaptor<Payment> paymentArgumentCaptor;

	@Captor
	ArgumentCaptor<String> paymentIdArgumentCaptor;

	@Test
	public void testShouldCreatePaymentWithCreditCard() {
		Payment dummyPayment = getDummyPayment();
		Payment dummyPaymentSaved = dummyPayment;
		dummyPaymentSaved.setId(new ObjectId());

		when(paymentProcess.process(dummyPayment)).thenReturn(dummyPayment);

		ResponseEntity<Payment> responseEntity = paymentRest.create(dummyPayment);

		verify(paymentProcess, times(1)).process(paymentArgumentCaptor.capture());
		verifyPayment(paymentArgumentCaptor.getValue(), dummyPayment);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	public void testShouldCreatePaymentWithSlip() {
		Payment dummyPayment = getDummyPayment();
		dummyPayment.setCreditCard(null);

		when(paymentProcess.process(dummyPayment)).thenReturn(dummyPayment);

		ResponseEntity<Payment> responseEntity = paymentRest.create(dummyPayment);
		verify(paymentProcess, times(1)).process(paymentArgumentCaptor.capture());
		verifyPayment(paymentArgumentCaptor.getValue(), dummyPayment);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	public void testShouldGetStatusByPaymentId() throws Exception {
		when(payments.findById(any(String.class))).thenReturn(Optional.of(getDummyPayment()));
		ResponseEntity<Optional<Payment>> responseEntity = paymentRest.getStatusByPaymentId("1111111");
		verify(payments, times(1)).findById(paymentIdArgumentCaptor.capture());
		assertEquals("1111111", paymentIdArgumentCaptor.getValue());
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	private void verifyPayment(Payment paymentCaptured, Payment payment) {
		assertEquals(payment, paymentCaptured);
	}

	@Test(expected = PaymentNonexistentException.class)
	public void testShouldReceiveNotFoundWhenGetStatusByPaymentNotExists() throws Exception {
		when(payments.findById(any(String.class))).thenReturn(Optional.empty());
		paymentRest.getStatusByPaymentId("abobora");
	}

	private Payment getDummyPayment() {
		Client client = new Client("123456789");
		Buyer buyer = new Buyer("paulo machado", "paulomachado@project.com", "46434925034");
		CreditCard creditCard = new CreditCard("paulo machado", "5131 7789 0356 3981", "08/2022", "736");
		Payment payment = new Payment(new ObjectId(), 100.1, Payment.PaymentType.CREDIT_CARD, client, buyer, creditCard,
				PaymentStatus.APPROVED, "1111111 1 2222222 2 333333 3 44444 4");
		return payment;
	}
}
