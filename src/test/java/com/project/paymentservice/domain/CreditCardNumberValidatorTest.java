package com.project.paymentservice.domain;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.validation.ConstraintValidator;

import org.junit.Before;
import org.junit.Test;

public class CreditCardNumberValidatorTest {

	private ConstraintValidator<CreditCardNumberContraint, String> validator;

	@Before
	public void setup() {
		validator = new CreditCardNumberValidator();
	}

	@Test
	public void testShoudToValidCreditCardNumberInvalid() throws Exception {
		assertFalse(validator.isValid("1234123412341234", null));
	}
	
	@Test
	public void testShoudToValidCreditCardNumberValid() throws Exception {
		assertTrue(validator.isValid("5131778903563981", null));
	}
}
