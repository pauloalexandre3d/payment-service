package com.project.paymentservice.interfaces;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.project.paymentservice.exception.PaymentNonexistentException;

@ControllerAdvice
public class CustomExceptionHandler {

	@ExceptionHandler(PaymentNonexistentException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public ErrorResponse handlePaymentNonexistentException(PaymentNonexistentException ex) {
		return createErrorResponse(ex.getMessage());
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public List<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> createErrorResponse(fieldError.getDefaultMessage()))
                .collect(Collectors.toList());
    }

	private ErrorResponse createErrorResponse(String message) {
		return new ErrorResponse(message);
	}

}