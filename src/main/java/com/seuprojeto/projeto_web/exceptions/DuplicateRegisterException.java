package com.seuprojeto.projeto_web.exceptions;

public class DuplicateRegisterException extends RuntimeException {
    public DuplicateRegisterException(String message) {
        super(message);
    }
}
