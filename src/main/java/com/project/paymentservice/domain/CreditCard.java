package com.project.paymentservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditCard {

	private String holderName;
	
	@CreditCardNumberContraint
	private String number;
	private String expirationDate;
	private String cvv;

}
