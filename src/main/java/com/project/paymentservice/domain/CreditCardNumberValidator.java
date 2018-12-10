package com.project.paymentservice.domain;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CreditCardNumberValidator implements ConstraintValidator<CreditCardNumberContraint, String> {

	@Override
	public boolean isValid(String creditCardNumber, ConstraintValidatorContext context) {
		return new br.com.moip.validators.CreditCard(creditCardNumber).isValid();
	}

}
