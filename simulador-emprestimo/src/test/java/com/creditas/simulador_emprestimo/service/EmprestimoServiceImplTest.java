package com.creditas.simulador_emprestimo.service;

import com.creditas.simulador_emprestimo.configs.properties.EnviaEmailProperties;
import com.creditas.simulador_emprestimo.configs.properties.TaxasJurosPorIdadeProperties;
import com.creditas.simulador_emprestimo.service.data.input.SimulacaoEmprestimoServiceInput;
import com.creditas.simulador_emprestimo.service.mappers.EmprestimoServiceMapper;
import com.creditas.simulador_emprestimo.service.data.output.SimulacaoEmprestimoServiceOutput;
import com.creditas.simulador_emprestimo.service.interfaces.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmprestimoServiceImplTest {

    private EmprestimoServiceMapper mapper;
    private EmailService emailService;
    private EmprestimoServiceImpl service;
    private EnviaEmailProperties enviaEmailProperties;

    @BeforeEach
    void setUp() {
        TaxasJurosPorIdadeProperties taxasJurosProperties = mock(TaxasJurosPorIdadeProperties.class);
        enviaEmailProperties = mock(EnviaEmailProperties.class);
        mapper = mock(EmprestimoServiceMapper.class);
        emailService = mock(EmailService.class);
        service = new EmprestimoServiceImpl(taxasJurosProperties, enviaEmailProperties, mapper, emailService);

        when(taxasJurosProperties.getAte25()).thenReturn(new BigDecimal("0.05"));
        when(taxasJurosProperties.getDe26a40()).thenReturn(new BigDecimal("0.03"));
        when(taxasJurosProperties.getDe41a60()).thenReturn(new BigDecimal("0.02"));
        when(taxasJurosProperties.getAcima60()).thenReturn(new BigDecimal("0.04"));
        when(enviaEmailProperties.isEnviaEmail()).thenReturn(true);
    }

    static Stream<Arguments> idadesETaxas() {
        return Stream.of(
                Arguments.of(20, new BigDecimal("0.05")), // <=25
                Arguments.of(30, new BigDecimal("0.03")), // <=40
                Arguments.of(50, new BigDecimal("0.02")), // <=60
                Arguments.of(70, new BigDecimal("0.04"))  // >60
        );
    }

    @Test
    void deveSimularEmprestimoEEnviarEmail() {
        var input = new SimulacaoEmprestimoServiceInput();
        input.setDataNascimento(LocalDate.now().minusYears(30));
        input.setValorEmprestimo(new BigDecimal("10000"));
        input.setPrazoMeses(12);

        var outputEsperado = mock(SimulacaoEmprestimoServiceOutput.class);

        when(enviaEmailProperties.isEnviaEmail()).thenReturn(true);
        when(mapper.toSimulacaoEmprestimoServiceOutput(any(), any(), any(), any(), any(), any()))
                .thenReturn(outputEsperado);

        var resultado = service.simular(input);

        assertNotNull(resultado);
        verify(emailService, times(1)).enviaEmail(outputEsperado);
    }

    @Test
    void deveSimularEmprestimoENAOEnviarEmail() {
        var input = new SimulacaoEmprestimoServiceInput();
        input.setDataNascimento(LocalDate.now().minusYears(30));
        input.setValorEmprestimo(new BigDecimal("10000"));
        input.setPrazoMeses(12);

        var outputEsperado = mock(SimulacaoEmprestimoServiceOutput.class);

        when(enviaEmailProperties.isEnviaEmail()).thenReturn(false);
        when(mapper.toSimulacaoEmprestimoServiceOutput(any(), any(), any(), any(), any(), any()))
                .thenReturn(outputEsperado);

        var resultado = service.simular(input);

        assertNotNull(resultado);
        verify(emailService, times(0)).enviaEmail(outputEsperado);
    }

    @ParameterizedTest
    @MethodSource("idadesETaxas")
    void deveRetornarTaxaCorretaPorIdade(int idade, BigDecimal taxaEsperada) throws Exception {
        TaxasJurosPorIdadeProperties taxas = mock(TaxasJurosPorIdadeProperties.class);
        when(taxas.getAte25()).thenReturn(new BigDecimal("0.05"));
        when(taxas.getDe26a40()).thenReturn(new BigDecimal("0.03"));
        when(taxas.getDe41a60()).thenReturn(new BigDecimal("0.02"));
        when(taxas.getAcima60()).thenReturn(new BigDecimal("0.04"));

        EmprestimoServiceImpl service = new EmprestimoServiceImpl(taxas, null,null, null);

        LocalDate nascimento = LocalDate.now().minusYears(idade);
        var method = service.getClass()
                .getDeclaredMethod("calcularTxAnualPelaIdadeCliente", LocalDate.class);
        method.setAccessible(true);
        BigDecimal taxa = (BigDecimal) method.invoke(service, nascimento);

        assertEquals(taxaEsperada, taxa);
    }

    @Test
    void naoEnviaEmailQuandoSimulacaoNula() {
        var input = new SimulacaoEmprestimoServiceInput();
        input.setDataNascimento(LocalDate.now().minusYears(30));
        input.setValorEmprestimo(new BigDecimal("10000"));
        input.setPrazoMeses(12);

        when(mapper.toSimulacaoEmprestimoServiceOutput(any(), any(), any(), any(), any(), any()))
                .thenReturn(null);

        var resultado = service.simular(input);

        assertNull(resultado);
        verify(emailService, never()).enviaEmail(any());
    }
}