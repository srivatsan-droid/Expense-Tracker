package com.expense_service.authService.ExceptionHandling;

public class EmailAlreadyExisitException extends RuntimeException {
    public EmailAlreadyExisitException(String message) {
        super(message);
    }
}
