package com.project.paymentservice.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;

import com.project.paymentservice.domain.Payment.PaymentStatus;

public class PaymentTest {

	private Validator validator;

	@Before
	public void setup() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void shouldValidateDummyPaymentClient() {
		Payment payment = getPaymentDummy();
		payment.setClient(null);
		Set<ConstraintViolation<Payment>> violations = validator.validate(payment);
		assertTrue(violations.size() == 1);
		assertTrue(violations.iterator().next().getMessage().equals(Payment.CLIENT_NULL_VALIDATION_MESSAGE));
	}

	@Test
	public void shouldValidateDummyPaymentClientId() {
		Payment payment = getPaymentDummy();
		payment.getClient().setId(null);
		Set<ConstraintViolation<Client>> violations = validator.validate(payment.getClient());
		violations.forEach(violation -> System.out.println(violation.getMessage()));
		assertTrue(violations.size() == 1);
		assertTrue(violations.iterator().next().getMessage().equals(Client.ID_NULL_VALIDATION_MESSAGE));
	}

	@Test
	public void shouldValidateDummyPaymentBuyer() {
		Payment payment = getPaymentDummy();
		payment.setBuyer(null);
		Set<ConstraintViolation<Payment>> violations = validator.validate(payment);
		assertTrue(violations.size() == 1);
		assertTrue(violations.iterator().next().getMessage().equals(Payment.BUYER_NULL_VALIDATION_MESSAGE));
	}

	@Test
	public void shouldValidateDummyPaymentBuyerName() {
		Payment payment = getPaymentDummy();
		payment.getBuyer().setName(null);
		Set<ConstraintViolation<Buyer>> violations = validator.validate(payment.getBuyer());
		assertTrue(violations.size() == 1);
		assertEquals(1, violations.stream().filter(viol -> viol.getMessage().equals(Buyer.NAME_NULL_VALIDATION_MESSAGE))
				.count());
	}

	@Test
	public void shouldValidateDummyPaymentBuyerNameInvalid() {
		Payment payment = getPaymentDummy();
		payment.getBuyer().setName("1234567890");
		Set<ConstraintViolation<Buyer>> violations = validator.validate(payment.getBuyer());
		assertTrue(violations.size() == 1);
		assertEquals(1, violations.stream().filter(viol -> viol.getMessage().equals(Buyer.NAME_FORMAT_INVALID_MESSAGE))
				.count());
	}

	@Test
	public void shouldValidateDummyPaymentBuyerEmail() {
		Payment payment = getPaymentDummy();
		payment.getBuyer().setEmail(null);
		Set<ConstraintViolation<Buyer>> violations = validator.validate(payment.getBuyer());
		assertTrue(violations.size() == 1);
		assertEquals(1, violations.stream()
				.filter(viol -> viol.getMessage().equals(Buyer.EMAIL_NULL_VALIDATION_MESSAGE)).count());
	}

	@Test
	public void shouldValidateDummyPaymentBuyerEmailInvalid() {
		Payment payment = getPaymentDummy();
		payment.getBuyer().setEmail("abobora");
		Set<ConstraintViolation<Buyer>> violations = validator.validate(payment.getBuyer());
		assertTrue(violations.size() == 1);
		assertEquals(1, violations.stream().filter(viol -> viol.getMessage().equals(Buyer.EMAIL_FORMAT_INVALID_MESSAGE))
				.count());
	}

	@Test
	public void shouldValidateDummyPaymentBuyerCpf() {
		Payment payment = getPaymentDummy();
		payment.getBuyer().setCpf(null);
		Set<ConstraintViolation<Buyer>> violations = validator.validate(payment.getBuyer());
		assertTrue(violations.size() == 1);
		assertTrue(violations.iterator().next().getMessage().equals(Buyer.CPF_NULL_VALIDATION_MESSAGE));
	}

	@Test
	public void shouldValidateDummyPaymentAmount() {
		Payment payment = getPaymentDummy();
		payment.setAmount(null);
		Set<ConstraintViolation<Payment>> violations = validator.validate(payment);
		assertTrue(violations.size() == 1);
		assertTrue(violations.iterator().next().getMessage().equals(Payment.AMOUNT_NULL_VALIDATION_MESSAGE));
	}

	@Test
	public void shouldPaymentHasStatus() {
		Payment payment = getPaymentDummy();
		payment.setStatus(PaymentStatus.APPROVED);
		assertEquals(PaymentStatus.APPROVED, payment.getStatus());
	}

	@Test
	public void shouldValidateDummyPaymentTypeCreditCard() {
		Payment payment = getPaymentDummy();
		assertEquals(payment.getType(), Payment.PaymentType.CREDIT_CARD);
	}

	@Test
	public void shouldValidateDummyPaymentTypeSlip() {
		Payment payment = getPaymentDummy();
		payment.setCreditCard(null);
		assertEquals(payment.getType(), Payment.PaymentType.SLIP);
	}

	@Test
	public void shouldValidateDummyPayment() {
		Payment payment = getPaymentDummy();
		Set<ConstraintViolation<Payment>> violations = validator.validate(payment);
		assertTrue(violations.isEmpty());
	}

	@Test
	public void shouldValidateDummyPaymentCreditCardInvalid() {
		Payment payment = getPaymentDummy();
		payment.getCreditCard().setNumber("1234123412341234");
		Set<ConstraintViolation<CreditCard>> violations = validator.validate(payment.getCreditCard());
		assertTrue(violations.size() == 1);
		assertTrue(violations.iterator().next().getMessage().equals("Credit card number is not valid."));
	}

	@Test
	public void shouldValidateDummyPaymentCreditCard() {
		Payment payment = getPaymentDummy();
		payment.getCreditCard().setNumber("5131778903563981");
		Set<ConstraintViolation<CreditCard>> violations = validator.validate(payment.getCreditCard());
		assertTrue(violations.size() == 0);
	}

	private Payment getPaymentDummy() {
		Client client = new Client("123456789");
		Buyer buyer = new Buyer("paulo machado", "paulomachado@project.com", "464.349.250-34");
		CreditCard creditCard = new CreditCard("paulo machado", "5131778903563981", "08/2022", "736");
		Payment payment = new Payment(new ObjectId(), 100.1, Payment.PaymentType.CREDIT_CARD, client, buyer, creditCard,
				PaymentStatus.APPROVED, "1111111 1 2222222 2 333333 3 44444 4");
		return payment;
	}

}
