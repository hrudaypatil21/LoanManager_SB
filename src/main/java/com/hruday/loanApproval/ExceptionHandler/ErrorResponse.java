package com.hruday.loanApproval.ExceptionHandler;

public class ErrorResponse {

    private int statusCode;
    private String message;

    // No-argument constructor
    public ErrorResponse() {
    }

    // All-arguments constructor
    public ErrorResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    // Constructor with only message
    public ErrorResponse(String message) {
        this.message = message;
    }

    // Getters and Setters
    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
