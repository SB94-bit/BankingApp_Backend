package com.BankingApp.exception;

public class AlreadyExistsException  extends RuntimeException {
    public AlreadyExistsException(String message) {
        super(message);
    }
}
