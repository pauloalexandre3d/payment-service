package com.project.paymentservice.interfaces;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.paymentservice.domain.Payment;
import com.project.paymentservice.exception.PaymentNonexistentException;
import com.project.paymentservice.repository.Payments;
import com.project.paymentservice.service.PaymentProcess;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Api(value = "/payment", consumes = MediaType.APPLICATION_JSON_VALUE)
public class PaymentController {

	private final Payments payments;
	
	private final PaymentProcess paymentProcess;

	@ApiOperation(value = "Create payment")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Payment created OK"),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 403, message = "Some issue regarding the input will cause the application not to proceed with the operation"),
			@ApiResponse(code = 409, message = "Conflict with the input provide"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@PostMapping("/payment")
	public ResponseEntity<Payment> create(@RequestBody @Valid Payment payment) {
		return ResponseEntity.ok(paymentProcess.process(payment));
	}

	@ApiOperation(value = "Get payment status by payment id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Payment found OK"),
			@ApiResponse(code = 400, message = "Bad Request"), 
			@ApiResponse(code = 404, message = "Payment not found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@GetMapping("/payment/{paymentId}/status")
	public ResponseEntity<Optional<Payment>> getStatusByPaymentId(@PathVariable String paymentId) {
		Optional<Payment> payment = payments.findById(paymentId);
		if (!payment.isPresent()) {
			throw new PaymentNonexistentException(paymentId);
		}
		return ResponseEntity.ok(payment);
	}

}
