package com.creditas.simulador_emprestimo.service.interfaces;

import com.creditas.simulador_emprestimo.service.data.output.SimulacaoEmprestimoServiceOutput;

public interface EmailService {
    void enviaEmail(SimulacaoEmprestimoServiceOutput simulacao);
}
