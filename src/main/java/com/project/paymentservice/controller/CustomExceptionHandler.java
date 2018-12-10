package com.project.paymentservice.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

@ControllerAdvice
public class CustomExceptionHandler {

//    @ExceptionHandler(UserAlreadyRegisteredException.class)
//    @ResponseStatus(HttpStatus.FORBIDDEN)
//    @ResponseBody
//    public ErrorResponse handleUserAlreadyRegisteredException(UserAlreadyRegisteredException ex) {
//        return createErrorResponse(ex.getMessage());
//    }
//
//    @ExceptionHandler(UsernameAlreadyRegisteredException.class)
//    @ResponseStatus(HttpStatus.FORBIDDEN)
//    @ResponseBody
//    public ErrorResponse handleUsernameAlreadyRegisteredException(UsernameAlreadyRegisteredException ex) {
//        return createErrorResponse(ex.getMessage());
//    }
//
//    @ExceptionHandler(UserBlacklistedException.class)
//    @ResponseStatus(HttpStatus.FORBIDDEN)
//    @ResponseBody
//    public ErrorResponse handleUserBlacklistedException(UserBlacklistedException ex) {
//        return createErrorResponse(ex.getMessage());
//    }

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

    @ExceptionHandler(InvalidFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleInvalidFormatException(InvalidFormatException ex) {
        return createErrorResponse(ex.getOriginalMessage());
    }

    private ErrorResponse createErrorResponse(String message) {
        return new ErrorResponse(message);
    }

}