package com.project.paymentservice.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Buyer {

	public static final String NAME_NULL_VALIDATION_MESSAGE = "Buyer name must not be null";
	public static final String NAME_FORMAT_INVALID_MESSAGE = "Name must be alphanumerical";

	public static final String EMAIL_NULL_VALIDATION_MESSAGE = "Buyer email must not be null";
	public static final String EMAIL_FORMAT_INVALID_MESSAGE = "Buyer email invalid format";

	public static final String CPF_NULL_VALIDATION_MESSAGE = "Buyer cpf must not be null";
	public static final String CPF_FORMAT_INVALID_MESSAGE = "Buyer cpf invalid format (999.999.999-99)";

	@NotNull(message = NAME_NULL_VALIDATION_MESSAGE)
	@Size(min = 1, message = "Buyer name must not be empty")
	@Pattern(regexp = "[[A-Z][a-z]* [A-Z][a-z]*]+", message = NAME_FORMAT_INVALID_MESSAGE)
	private String name;

	@NotNull(message = EMAIL_NULL_VALIDATION_MESSAGE)
	@Size(min = 3)
	@Pattern(message = EMAIL_FORMAT_INVALID_MESSAGE, regexp = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$")
	private String email;

	@NotNull(message = CPF_NULL_VALIDATION_MESSAGE)
	@Size(min = 11)
	@Pattern(regexp = "([0-9]{2}[\\\\.]?[0-9]{3}[\\\\.]?[0-9]{3}[\\/]?[0-9]{4}[-]?[0-9]{2})|([0-9]{3}[\\\\.]?[0-9]{3}[\\\\.]?[0-9]{3}[-]?[0-9]{2})", message = CPF_FORMAT_INVALID_MESSAGE)
	private String cpf;
}
