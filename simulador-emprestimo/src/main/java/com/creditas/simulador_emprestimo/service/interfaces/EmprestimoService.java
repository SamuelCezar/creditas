package com.creditas.simulador_emprestimo.service.interfaces;

import com.creditas.simulador_emprestimo.service.data.input.SimulacaoEmprestimoServiceInput;
import com.creditas.simulador_emprestimo.service.data.output.SimulacaoEmprestimoServiceOutput;

public interface EmprestimoService {
    SimulacaoEmprestimoServiceOutput simular(SimulacaoEmprestimoServiceInput input);
}
