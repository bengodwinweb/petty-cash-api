package com.bengodwinweb.pettycash.exception;

public class EmailExistsException extends Exception {
    public EmailExistsException() {
        super("Email already exists");
    }
}
