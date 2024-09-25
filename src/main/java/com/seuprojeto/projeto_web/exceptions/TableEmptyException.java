package com.seuprojeto.projeto_web.exceptions;

public class TableEmptyException extends RuntimeException {
    public TableEmptyException(String message) {
        super(message);
    }
}
