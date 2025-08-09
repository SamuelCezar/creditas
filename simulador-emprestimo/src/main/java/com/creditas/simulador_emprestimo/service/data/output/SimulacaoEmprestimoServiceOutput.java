package com.creditas.simulador_emprestimo.service.data.output;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SimulacaoEmprestimoServiceOutput {

    private BigDecimal valorEmprestimo;
    private int prazoMeses;
    private BigDecimal valorTotalAPagar;
    private BigDecimal parcelaMensal;
    private BigDecimal totalJurosPagos;
    private BigDecimal jurosAnual;
    private BigDecimal jurosMensal;

}
