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
public class Client {

	public static final String ID_NULL_VALIDATION_MESSAGE = "Client ID must not be null";
	
	@NotNull(message = ID_NULL_VALIDATION_MESSAGE)
    @Size(min=1, message="Client ID not be empty")
	private String id;
}
