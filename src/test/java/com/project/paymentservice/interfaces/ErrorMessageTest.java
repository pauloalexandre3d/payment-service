package com.project.paymentservice.interfaces;

import static org.junit.Assert.*;

import org.junit.Test;

import com.project.paymentservice.interfaces.ErrorResponse;

public class ErrorMessageTest {

	@Test
	public void testShouldAssertErrorResponseExists() {
		ErrorResponse errorResponse = new ErrorResponse("Errorrr");
		assertEquals("Errorrr", errorResponse.getError());
	}

}
