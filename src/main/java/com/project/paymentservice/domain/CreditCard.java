package com.project.paymentservice.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreditCard {

	@NotNull(message = "Credit card holder name must not be null.")
    @Size(min=1, message="Credit card holder name must not be empty.")
	private String holderName;
	
	@NotNull(message = "Credit Card number must not be null.")
    @Size(min=16, message="Credit Card number must not be empty.")
	@CreditCardNumberContraint
	private String number;
	
	@NotNull(message = "Credit Card expiration date must not be null.")
    @Size(min=1, message="Credit Card expiration date must not be empty.")
	private String expirationDate;
	
	@NotNull(message = "Credit Card CVV must not be null.")
    @Size(min=3, message="Credit Card CVV must not be empty.")
	private String cvv;

	public String  getBrand() {
		return new  br.com.moip.validators.CreditCard(this.getNumber()).getBrand().name();
	}

}
