package com.seuprojeto.projeto_web.exceptions;

public class FieldInvalidException extends RuntimeException {
    public FieldInvalidException(String message) {
        super(message);
    }
}
