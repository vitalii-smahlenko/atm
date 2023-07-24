package com.gmail.smaglenko.atmapp.exception;

public class InvalidBanknoteAmountException extends RuntimeException {
    public InvalidBanknoteAmountException(String message) {
        super(message);
    }
}