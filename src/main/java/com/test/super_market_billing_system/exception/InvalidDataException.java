package com.test.super_market_billing_system.exception;

public class InvalidDataException extends Exception{

    private static final long serialVersionUID = 7537167259815318140L;

    private int status;

    public InvalidDataException(int status, String message) {
        super(message);
        this.status = status;
    }

}
