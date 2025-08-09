package com.creditas.simulador_emprestimo.controller;

import com.creditas.simulador_emprestimo.controller.data.request.SimulacaoRequest;
import com.creditas.simulador_emprestimo.controller.data.response.SimulacaoResponse;
import com.creditas.simulador_emprestimo.controller.mappers.EmprestimoControllerMapper;
import com.creditas.simulador_emprestimo.controller.mappers.EmprestimoControllerMapperImpl;
import com.creditas.simulador_emprestimo.exception.handler.ControllerExceptionHandler;
import com.creditas.simulador_emprestimo.service.data.output.SimulacaoEmprestimoServiceOutput;
import com.creditas.simulador_emprestimo.service.interfaces.EmprestimoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

class EmprestimoControllerTest {

    @Mock
    private EmprestimoService emprestimoService;

    @Spy
    private EmprestimoControllerMapper mapper = new EmprestimoControllerMapperImpl();

    @InjectMocks
    private EmprestimoController controller;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = standaloneSetup(controller)
                .setControllerAdvice(new ControllerExceptionHandler()).build();
        objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Test
    @DisplayName("Espera-se retornar ResponseEntity com SimulacaoResponse")
    void deveRetornarResponseEntityComSimulacaoResponse() {
        SimulacaoRequest request = mock(SimulacaoRequest.class);
        SimulacaoEmprestimoServiceOutput output = buildServiceOutput();
        SimulacaoResponse response = buildResponse();

        when(emprestimoService.simular(any())).thenReturn(output);

        ResponseEntity<SimulacaoResponse> result = controller.simular(request);

        assertNotNull(result);
        assertEquals(response.getValorEmprestimo(), result.getBody().getValorEmprestimo());
        assertEquals(response.getParcelaMensal(), result.getBody().getParcelaMensal());
        assertEquals(response.getPrazoMeses(), result.getBody().getPrazoMeses());
        assertEquals(response.getValorTotalAPagar(), result.getBody().getValorTotalAPagar());
        assertEquals(response.getTotalJurosPagos(), result.getBody().getTotalJurosPagos());
        assertEquals(response.getJurosAnual(), result.getBody().getJurosAnual());
        assertEquals(response.getParcelaMensal(), result.getBody().getParcelaMensal());
    }

    @Test
    @DisplayName("Espera-se retornar erro quando prazoMeses for null")
    void deveRetornarErroQuandoPrazoMesesNaoInformada() throws Exception {
        SimulacaoRequest request = new SimulacaoRequest();
        request.setValorEmprestimo(new BigDecimal("1000"));
        request.setDataNascimento(LocalDate.of(1990, 1, 14));

        mockMvc.perform(post("/api/emprestimos/simular")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("[\"Atenção: 'prazoMeses' deve ser maior que zero.\"]"));
    }

    @Test
    @DisplayName("Espera-se retornar erro quando dataNascimento for null")
    void deveRetornarErroQuandoDataNascimentoNaoInformada() throws Exception {
        SimulacaoRequest request = new SimulacaoRequest();
        request.setValorEmprestimo(new BigDecimal("1000"));
        request.setPrazoMeses(20);

        mockMvc.perform(post("/api/emprestimos/simular")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("[\"Atenção: 'dataNascimento' não pode ser nula.\"]"));
    }

    @Test
    @DisplayName("Espera-se retornar erro quando valorEmprestimo não for null")
    void deveRetornarErroQuandoValorEmprestimoNaoInformada() throws Exception {
        SimulacaoRequest request = new SimulacaoRequest();
        request.setPrazoMeses(25);
        request.setDataNascimento(LocalDate.of(1990, 1, 14));

        mockMvc.perform(post("/api/emprestimos/simular")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("[\"Atenção: 'valorEmprestimo' não pode ser nulo.\"]"));
    }


    private SimulacaoEmprestimoServiceOutput buildServiceOutput() {

        SimulacaoEmprestimoServiceOutput output = new SimulacaoEmprestimoServiceOutput();
        output.setValorEmprestimo(new BigDecimal("1200.00"));
        output.setParcelaMensal(new BigDecimal("60.00"));
        output.setPrazoMeses(20);
        output.setValorTotalAPagar(new BigDecimal("1500.00"));
        output.setTotalJurosPagos(new BigDecimal("300.00"));
        output.setJurosAnual(new BigDecimal("0.25"));
        output.setJurosMensal(new BigDecimal("0.015"));
        return output;
    }

    private SimulacaoResponse buildResponse() {

        SimulacaoResponse response = new SimulacaoResponse();
        response.setValorEmprestimo(new BigDecimal("1200.00"));
        response.setParcelaMensal(new BigDecimal("60.00"));
        response.setPrazoMeses(20);
        response.setValorTotalAPagar(new BigDecimal("1500.00"));
        response.setTotalJurosPagos(new BigDecimal("300.00"));
        response.setJurosAnual(new BigDecimal("0.25"));
        response.setJurosMensal(new BigDecimal("0.015"));
        return response;
    }
}
