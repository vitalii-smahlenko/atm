package com.gmail.smaglenko.atmapp.exception;

public class InvalidBanknoteException extends RuntimeException {
    public InvalidBanknoteException(String message) {
        super(message);
    }
}