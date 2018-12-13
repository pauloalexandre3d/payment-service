package com.project.paymentservice.domain;

import static org.junit.Assert.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;

public class CreditCardTest {

	private Validator validator;

	@Before
	public void setUp() throws Exception {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void shouldValidateDummyCreditCardHolderNameNull() {
		CreditCard creditCardDummy = getCreditCardDummy();
		creditCardDummy.setHolderName(null);
		Set<ConstraintViolation<CreditCard>> violations = validator.validate(creditCardDummy);
		assertTrue(violations.size() == 1);
		assertTrue(violations.iterator().next().getMessage().equals("Credit card holder name must not be null."));
	}

	@Test
	public void shouldValidateDummyCreditCardHolderNameEmpty() {
		CreditCard creditCardDummy = getCreditCardDummy();
		creditCardDummy.setHolderName("");
		Set<ConstraintViolation<CreditCard>> violations = validator.validate(creditCardDummy);
		assertTrue(violations.size() == 1);
		assertTrue(violations.iterator().next().getMessage().equals("Credit card holder name must not be empty."));
	}

	@Test
	public void shouldValidateDummyCreditCardHolderNumberNull() {
		CreditCard creditCardDummy = getCreditCardDummy();
		creditCardDummy.setNumber(null);
		Set<ConstraintViolation<CreditCard>> violations = validator.validate(creditCardDummy);
		assertTrue(violations.size() == 2);
		assertEquals(1, violations.stream()
				.filter(viol -> viol.getMessage().equals("Credit Card number must not be null.")).count());
		assertEquals(1, violations.stream()
				.filter(viol -> viol.getMessage().equals("Credit card number is not valid.")).count());
	}

	@Test
	public void shouldValidateDummyCreditCardHolderNumberEmpty() {
		CreditCard creditCardDummy = getCreditCardDummy();
		creditCardDummy.setNumber("");
		Set<ConstraintViolation<CreditCard>> violations = validator.validate(creditCardDummy);
		assertTrue(violations.size() == 2);
		assertEquals(1, violations.stream()
				.filter(viol -> viol.getMessage().equals("Credit Card number must not be empty.")).count());
		assertEquals(1, violations.stream()
				.filter(viol -> viol.getMessage().equals("Credit card number is not valid.")).count());
	}
	
	@Test
	public void shouldValidateDummyCreditCardExpirationDateNull() {
		CreditCard creditCardDummy = getCreditCardDummy();
		creditCardDummy.setExpirationDate(null);
		Set<ConstraintViolation<CreditCard>> violations = validator.validate(creditCardDummy);
		assertTrue(violations.size() == 1);
		assertEquals(1, violations.stream()
				.filter(viol -> viol.getMessage().equals("Credit Card expiration date must not be null.")).count());
	}

	@Test
	public void shouldValidateDummyCreditCardExpirationDateEmpty() {
		CreditCard creditCardDummy = getCreditCardDummy();
		creditCardDummy.setExpirationDate("");
		Set<ConstraintViolation<CreditCard>> violations = validator.validate(creditCardDummy);
		assertTrue(violations.size() == 1);
		assertEquals(1, violations.stream()
				.filter(viol -> viol.getMessage().equals("Credit Card expiration date must not be empty.")).count());
	}
	
	@Test
	public void shouldValidateDummyCreditCardCvvNull() {
		CreditCard creditCardDummy = getCreditCardDummy();
		creditCardDummy.setCvv(null);
		Set<ConstraintViolation<CreditCard>> violations = validator.validate(creditCardDummy);
		assertTrue(violations.size() == 1);
		assertEquals(1, violations.stream()
				.filter(viol -> viol.getMessage().equals("Credit Card CVV must not be null.")).count());
	}

	@Test
	public void shouldValidateDummyCreditCardCvvEmpty() {
		CreditCard creditCardDummy = getCreditCardDummy();
		creditCardDummy.setCvv("");
		Set<ConstraintViolation<CreditCard>> violations = validator.validate(creditCardDummy);
		assertTrue(violations.size() == 1);
		assertEquals(1, violations.stream()
				.filter(viol -> viol.getMessage().equals("Credit Card CVV must not be empty.")).count());
	}
	
	@Test
	public void shouldGetCreditCardBrand() throws Exception {
		CreditCard creditCardDummy = getCreditCardDummy();
		assertEquals("MASTERCARD", creditCardDummy.getBrand());
	}
	
	private CreditCard getCreditCardDummy() {
		return new CreditCard("paulo machado", "5131778903563981", "08/2022", "736");
	}

}
