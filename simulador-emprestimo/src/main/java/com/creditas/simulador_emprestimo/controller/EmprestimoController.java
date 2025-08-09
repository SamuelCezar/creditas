package com.creditas.simulador_emprestimo.controller;

import com.creditas.simulador_emprestimo.controller.data.request.SimulacaoRequest;
import com.creditas.simulador_emprestimo.controller.data.response.SimulacaoResponse;
import com.creditas.simulador_emprestimo.controller.mappers.EmprestimoControllerMapper;
import com.creditas.simulador_emprestimo.service.data.output.SimulacaoEmprestimoServiceOutput;
import com.creditas.simulador_emprestimo.service.interfaces.EmprestimoService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(
                title = "Simulação de Empréstimos",
                description = "Simula empréstimos com diferentes taxas de juros a partir da idade, valor e prazo."
        ))
@RestController
@RequestMapping("/api/emprestimos")
@RequiredArgsConstructor
public class EmprestimoController {

    private final EmprestimoService emprestimoService;
    private final EmprestimoControllerMapper mapper;

    @Operation(summary = "Realiza uma simulação de empréstimo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Simulação realizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping("/simular")
    public ResponseEntity<SimulacaoResponse> simular(@Valid @RequestBody SimulacaoRequest request) {

        SimulacaoEmprestimoServiceOutput simulacao = emprestimoService.simular(mapper.toSimulacaoServiceInput(request));
        SimulacaoResponse response = mapper.toSimulacaoEmprestimoResponse(simulacao);

        return ResponseEntity.ok(response);
    }

}
