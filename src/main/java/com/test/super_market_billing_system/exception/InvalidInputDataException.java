package com.test.super_market_billing_system.exception;

public class InvalidInputDataException extends Exception{

    private static final long serialVersionUID = 7537167259815318140L;

    private int status;

    public InvalidInputDataException(int status, String message) {
        super(message);
        this.status = status;
    }

}
