package com.paltecno.fintech.invoicing.exception;

public class ApiException extends RuntimeException{
    public ApiException(String message) {
        super(message);
    }
}
