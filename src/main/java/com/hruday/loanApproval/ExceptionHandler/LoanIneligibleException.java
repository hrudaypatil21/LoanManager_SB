package com.hruday.loanApproval.ExceptionHandler;

public class LoanIneligibleException extends RuntimeException{

    private String message;

    public LoanIneligibleException() {}

    public LoanIneligibleException(String message) {
        super(message);
        this.message = message;
    }

}
