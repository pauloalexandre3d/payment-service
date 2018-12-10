package com.project.paymentservice.controller;

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
import com.project.paymentservice.domain.Payment.PaymentStatus;
import com.project.paymentservice.domain.Payment.PaymentType;
import com.project.paymentservice.repository.Payments;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Api(value = "/payment", consumes = MediaType.APPLICATION_JSON_VALUE)
public class PaymentRest {
	
	private final Payments payments;

	@ApiOperation(value = "Create payment")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Payment created OK"),
                    @ApiResponse(code = 400, message = "Bad Request"),
                    @ApiResponse(code = 403, message = "Some issue regarding the input will cause the application not to proceed with the operation"),
                    @ApiResponse(code = 409, message = "Conflict with the input provide"),
                    @ApiResponse(code = 500, message = "Internal Server Error")
            }
    )
    @PostMapping("/payment")
	public ResponseEntity<String> create(@RequestBody @Valid Payment payment) {
		payment.setStatus(PaymentStatus.APPROVED);
		Payment paymentSaved = payments.save(payment);

		if (payment.getType().equals(PaymentType.SLIP)) {
			return ResponseEntity.ok("1111111 1 2222222 2 333333 3 44444 4");
		} else {
			return ResponseEntity.ok(paymentSaved.getId().toString());
		}
	}

	@ApiOperation(value = "Get payment status by payment id")
	@GetMapping("/payment/{paymentId}/status")
	public ResponseEntity<Optional<Payment>> getStatusByPaymentId(@PathVariable String paymentId) {
		Optional<Payment> payment = payments.findById(paymentId);
		return ResponseEntity.ok(payment);
	}

}
