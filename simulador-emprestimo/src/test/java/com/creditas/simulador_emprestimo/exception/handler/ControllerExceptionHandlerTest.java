package com.creditas.simulador_emprestimo.exception.handler;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ControllerExceptionHandlerTest {

    private final ControllerExceptionHandler exceptionHandler = new ControllerExceptionHandler();

    @Test
    @DisplayName("Espera-se um  BAD_REQUEST e mensagem configurada para HttpMessageNotReadableException")
    void testMessageNotReadableException() {
        HttpMessageNotReadableException ex = Mockito.mock(HttpMessageNotReadableException.class);

        ResponseEntity<String> response = exceptionHandler.messageNotReadableException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Falha na leitura da requisição: Por favor, verifique se os dados enviados estão corretos.", response.getBody());
    }

    @Test
    @DisplayName("Espera-se BAD_REQUEST e lista de mensagens para MethodArgumentNotValidException")
    void testMethodArgumentNotValidException() {

        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        List<FieldError> fieldErrors = new ArrayList<>();
        fieldErrors.add(new FieldError("SimularEmprestimoRequest", "dataNascimento", "Atenção: 'dataNascimento' não pode ser nula."));
        fieldErrors.add(new FieldError("SimularEmprestimoRequest", "valorEmprestimo", "Atenção: 'valorEmprestimo' não pode ser nulo."));

        Mockito.when(bindingResult.getFieldErrors()).thenReturn(fieldErrors);

        MethodArgumentNotValidException ex = Mockito.mock(MethodArgumentNotValidException.class);
        Mockito.when(ex.getBindingResult()).thenReturn(bindingResult);

        ResponseEntity<ArrayList<String>> response = exceptionHandler.methodArgumentNotValidException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().contains("Atenção: 'dataNascimento' não pode ser nula."));
        assertTrue(response.getBody().contains("Atenção: 'valorEmprestimo' não pode ser nulo."));
    }

    @Test
    @DisplayName("Espera-se BAD_REQUEST e mensagem de erro inesperado para Exception genérica")
    void testHandleException() {

        Exception ex = new Exception("Falha inesperada");

        ResponseEntity<String> response = exceptionHandler.handleException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Ocorreu um erro inesperado por favor tente novamente: Falha inesperada", response.getBody());
    }
}
