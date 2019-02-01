package com.project.paymentservice.interfaces;

import java.io.Serializable;

import lombok.Getter;

@Getter
public class ErrorResponse implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6523609406142456613L;
	private String error;

    public ErrorResponse(String error) {
        this.error = error;
    }
}
