package com.creditas.simulador_emprestimo.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<String> messageNotReadableException(final HttpMessageNotReadableException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Falha na leitura da requisição: Por favor, verifique se os dados enviados estão corretos.");
    }

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    public ResponseEntity<String> handleException(final Exception e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Ocorreu um erro inesperado por favor tente novamente: " + e.getMessage());
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ArrayList<String>> methodArgumentNotValidException(final MethodArgumentNotValidException e) {
        Set<String> mensagens = new LinkedHashSet<>();
        e.getBindingResult().getFieldErrors().forEach(error -> mensagens.add(error.getDefaultMessage()));
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ArrayList<>(mensagens));
    }
}