package com.project.paymentservice.controller;

import static org.junit.Assert.assertEquals;
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
import com.project.paymentservice.repository.Payments;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class PaymentControllerTest {

    @InjectMocks
    PaymentRest paymentRest;

    @Mock
    Payments payments;

    @Captor
    ArgumentCaptor<Payment> paymentArgumentCaptor;
    
    @Captor
    ArgumentCaptor<String> paymentIdArgumentCaptor;

    @Test
    public void testShouldCreatePaymentWithCreditCard(){
    	Payment dummyPayment = getDummyPayment();
		Payment dummyPaymentSaved = dummyPayment;
    	dummyPaymentSaved.setId(new ObjectId());
    	when(payments.save(dummyPayment)).thenReturn(dummyPaymentSaved);
        ResponseEntity<String> responseEntity = paymentRest.create(dummyPayment);
        verify(payments, times(1)).save(paymentArgumentCaptor.capture());
        verifyPayment(paymentArgumentCaptor.getValue(), dummyPayment);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
    
    @Test
    public void testShouldCreatePaymentWithSlip(){
        Payment dummyPayment = getDummyPayment();
        dummyPayment.setCreditCard(null);
		ResponseEntity<String> responseEntity = paymentRest.create(dummyPayment);
        verify(payments, times(1)).save(paymentArgumentCaptor.capture());
        verifyPayment(paymentArgumentCaptor.getValue(), dummyPayment);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
    
    @Test
	public void testShouldGetStatusByPaymentId() throws Exception {
    	 ResponseEntity<Optional<Payment>> responseEntity = paymentRest.getStatusByPaymentId("1111111");
         verify(payments, times(1)).findById(paymentIdArgumentCaptor.capture());
         assertEquals("1111111", paymentIdArgumentCaptor.getValue());
         assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

    private void verifyPayment(Payment paymentCaptured, Payment payment){
        assertEquals(payment, paymentCaptured);
    }

    private Payment getDummyPayment(){
    	Client client = new Client("123456789");
		Buyer buyer = new Buyer("paulo machado", "paulomachado@project.com", "46434925034");
		CreditCard creditCard = new CreditCard("paulo machado", "5131 7789 0356 3981", "08/2022", "736");
		Payment payment = new Payment(null, 100.1, Payment.PaymentType.CREDIT_CARD, client, buyer, creditCard, PaymentStatus.APPROVED);
		return payment ;
    }
}