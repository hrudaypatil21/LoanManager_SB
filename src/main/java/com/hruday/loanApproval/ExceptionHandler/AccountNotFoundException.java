package com.hruday.loanApproval.ExceptionHandler;

public class AccountNotFoundException extends RuntimeException{

    private String message;

    public AccountNotFoundException() {}

    public AccountNotFoundException(String message) {
        super(message);
        this.message = message;
    }

}
