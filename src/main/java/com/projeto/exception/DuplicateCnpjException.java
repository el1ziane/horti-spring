package com.projeto.exception;

public class DuplicateCnpjException extends RuntimeException {
    public DuplicateCnpjException(String message) {
        super(message);
    }
}
