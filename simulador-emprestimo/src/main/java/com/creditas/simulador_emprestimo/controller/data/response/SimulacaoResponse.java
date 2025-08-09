package com.creditas.simulador_emprestimo.controller.data.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SimulacaoResponse {

    @Schema(description = "Valor solicitado pelo cliente no empréstimo", example = "1500.00")
    private BigDecimal valorEmprestimo;

    @Schema(description = "Prazo para pagamento em meses", example = "24")
    private int prazoMeses;

    @Schema(description = "Valor total a ser pago pelo cliente ao final do emréstimo", example = "1625.80")
    private BigDecimal valorTotalAPagar;

    @Schema(description = "Valor de cada parcela mensal", example = "70.00")
    private BigDecimal parcelaMensal;

    @Schema(description = "Total de juros pagos ao longo do prazo", example = "125.80")
    private BigDecimal totalJurosPagos;

    @Schema(description = "Taxa de juros anual calculada", example = "0.03")
    private BigDecimal jurosAnual;

    @Schema(description = "Taxa de juros mensal calculada", example = "0.003")
    private BigDecimal jurosMensal;
}
