package com.creditas.simulador_emprestimo.service.data.input;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class SimulacaoEmprestimoServiceInput {

    private BigDecimal valorEmprestimo;
    private LocalDate dataNascimento;
    private int prazoMeses;
}
