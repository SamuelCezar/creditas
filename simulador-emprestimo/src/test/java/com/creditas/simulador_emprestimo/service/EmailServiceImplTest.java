package com.creditas.simulador_emprestimo.service;

import com.creditas.simulador_emprestimo.service.data.output.SimulacaoEmprestimoServiceOutput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmailServiceImplTest {

    private JavaMailSender mailSender;
    private EmailServiceImpl emailService;

    @BeforeEach
    void setUp() {
        mailSender = mock(JavaMailSender.class);
        emailService = new EmailServiceImpl(mailSender);
    }

    @Test
    void deveEnviarEmailComCorpoCorreto() {
        SimulacaoEmprestimoServiceOutput simulacao = mock(SimulacaoEmprestimoServiceOutput.class);
        when(simulacao.getValorEmprestimo()).thenReturn(new BigDecimal("10000"));
        when(simulacao.getPrazoMeses()).thenReturn(12);
        when(simulacao.getJurosAnual()).thenReturn(new BigDecimal("0.10"));
        when(simulacao.getJurosMensal()).thenReturn(new BigDecimal("0.008"));
        when(simulacao.getParcelaMensal()).thenReturn(new BigDecimal("900"));
        when(simulacao.getValorTotalAPagar()).thenReturn(new BigDecimal("10800"));
        when(simulacao.getTotalJurosPagos()).thenReturn(new BigDecimal("800"));

        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);

        emailService.enviaEmail(simulacao);

        verify(mailSender, times(1)).send(captor.capture());
        SimpleMailMessage email = captor.getValue();

        assertEquals("samuelcezar@outlook.com", email.getTo()[0]);
        assertEquals("Simulação de empréstimo está pronta!", email.getSubject());
        assertTrue(email.getText().contains("Valor do Empréstimo: 10000"));
        assertTrue(email.getText().contains("Prazo (meses): 12"));
        assertTrue(email.getText().contains("Taxa de Juros Anual: 0.10"));
        assertTrue(email.getText().contains("Taxa de Juros Mensal: 0.008"));
        assertTrue(email.getText().contains("Parcela Mensal: 900"));
        assertTrue(email.getText().contains("Valor Total a Pagar: 10800"));
        assertTrue(email.getText().contains("Total de Juros Pagos: 800"));
    }
}
