package com.bengodwinweb.pettycash.exception;

public class CustomExpiredJwtException extends Exception {
    public CustomExpiredJwtException() {
        super("JWT Expired");
    }
}
