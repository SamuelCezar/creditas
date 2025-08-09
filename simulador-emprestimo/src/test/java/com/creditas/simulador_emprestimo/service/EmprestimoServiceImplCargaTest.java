package com.creditas.simulador_emprestimo.service;

import com.creditas.simulador_emprestimo.configs.properties.EnviaEmailProperties;
import com.creditas.simulador_emprestimo.configs.properties.TaxasJurosPorIdadeProperties;
import com.creditas.simulador_emprestimo.service.data.input.SimulacaoEmprestimoServiceInput;
import com.creditas.simulador_emprestimo.service.mappers.EmprestimoServiceMapper;
import com.creditas.simulador_emprestimo.service.data.output.SimulacaoEmprestimoServiceOutput;
import com.creditas.simulador_emprestimo.service.interfaces.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EmprestimoServiceImplCargaTest {

    @InjectMocks
    private EmprestimoServiceImpl service;

    @BeforeEach
    void setUp() {
        TaxasJurosPorIdadeProperties taxasJurosProperties = mock(TaxasJurosPorIdadeProperties.class);
        EnviaEmailProperties enviaEmailProperties = mock(EnviaEmailProperties.class);
        when(taxasJurosProperties.getAte25()).thenReturn(new BigDecimal("0.05"));
        when(taxasJurosProperties.getDe26a40()).thenReturn(new BigDecimal("0.03"));
        when(taxasJurosProperties.getDe41a60()).thenReturn(new BigDecimal("0.02"));
        when(taxasJurosProperties.getAcima60()).thenReturn(new BigDecimal("0.04"));

        EmprestimoServiceMapper mapper = (input, txAnual, txMensal, total, juros, parcela) ->
                new SimulacaoEmprestimoServiceOutput();
        EmailService emailService = mock(EmailService.class);

        service = new EmprestimoServiceImpl(taxasJurosProperties, enviaEmailProperties, mapper, emailService);
    }

    @Test
    @DisplayName("Teste de carga: simulações em lote com dados iguais")
    void testDesempenhoCalculoEmprestimo() {

        SimulacaoEmprestimoServiceInput input = new SimulacaoEmprestimoServiceInput();
        input.setDataNascimento(LocalDate.now().minusYears(36));
        input.setValorEmprestimo(BigDecimal.valueOf(12000));
        input.setPrazoMeses(36);

        int iteracoes = 500000;
        long start = System.nanoTime();

        for (int i = 0; i < iteracoes; i++) {
            SimulacaoEmprestimoServiceOutput resultado = service.simular(input);
            assertNotNull(resultado);
        }

        long end = System.nanoTime();
        long duracaoMs = (end - start) / 1000000;
        System.out.println(new DecimalFormat("#,###").format(iteracoes) +
                " execuções. Tempo total gasto: " + new java.text.DecimalFormat("#,###").format(duracaoMs) + " ms");
        System.out.println("Tempo médio por execução: " + (duracaoMs / (double) iteracoes) + " ms");
    }
}