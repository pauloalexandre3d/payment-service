package com.project.paymentservice.controller;

import static org.junit.Assert.*;

import org.junit.Test;

public class ErrorMessageTest {

	@Test
	public void testShouldAssertErrorResponseExists() {
		ErrorResponse errorResponse = new ErrorResponse("Errorrr");
		assertEquals("Errorrr", errorResponse.getError());
	}

}
