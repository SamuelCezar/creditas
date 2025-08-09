package com.creditas.simulador_emprestimo.controller.data.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class SimulacaoRequest {

    @NotNull(message = "Atenção: 'valorEmprestimo' não pode ser nulo.")
    @Schema(description = "Valor do empréstimo solicitado", example = "1500.00")
    private BigDecimal valorEmprestimo;

    @NotNull(message = "Atenção: 'dataNascimento' não pode ser nula.")
    @Schema(description = "Data de nascimento do solicitante", example = "1988-12-17")
    private LocalDate dataNascimento;

    @Min(value = 1, message = "Atenção: 'prazoMeses' deve ser maior que zero.")
    @Schema(description = "Prazo para pagamento em meses", example = "12")
    private int prazoMeses;
}
