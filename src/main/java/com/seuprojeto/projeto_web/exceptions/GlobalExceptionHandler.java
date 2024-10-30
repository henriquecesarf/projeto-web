package com.seuprojeto.projeto_web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
@ControllerAdvice
public class GlobalExceptionHandler {

    // Tratamento para várias exceções personalizadas
    @ExceptionHandler({ FieldNotFoundException.class, DuplicateRegisterException.class, FieldInvalidException.class })
    public ResponseEntity<Map<String, String>> handleCustomExceptions(Exception exception) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", exception.getMessage());

        if (exception instanceof FieldNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

        if (exception instanceof DuplicateRegisterException || exception instanceof FieldInvalidException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    // Tratamento específico para TableEmptyException
    @ExceptionHandler(TableEmptyException.class)
    public ResponseEntity<Map<String, String>> handleTableEmptyException(TableEmptyException exception) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", exception.getMessage());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(errorResponse); // Retorna 204 se a tabela estiver vazia
    }

    // Tratamento para exceções de validação
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errorResponse = new HashMap<>();

        FieldError fieldError = ex.getBindingResult().getFieldError();
        if (fieldError != null) {
            errorResponse.put("field", fieldError.getField());
            errorResponse.put("message", fieldError.getDefaultMessage());
        } else {
            errorResponse.put("message", "Erro de validação desconhecido");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}