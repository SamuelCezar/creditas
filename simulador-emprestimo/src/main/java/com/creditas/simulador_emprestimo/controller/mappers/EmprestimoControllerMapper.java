package com.creditas.simulador_emprestimo.controller.mappers;

import com.creditas.simulador_emprestimo.configs.mappers.MapperStructConfig;
import com.creditas.simulador_emprestimo.controller.data.request.SimulacaoRequest;
import com.creditas.simulador_emprestimo.controller.data.response.SimulacaoResponse;
import com.creditas.simulador_emprestimo.service.data.input.SimulacaoEmprestimoServiceInput;
import com.creditas.simulador_emprestimo.service.data.output.SimulacaoEmprestimoServiceOutput;
import org.mapstruct.Mapper;

@Mapper(config = MapperStructConfig.class)
public interface EmprestimoControllerMapper {
    
    SimulacaoEmprestimoServiceInput toSimulacaoServiceInput(SimulacaoRequest simulacaoRequest);

    SimulacaoResponse toSimulacaoEmprestimoResponse(SimulacaoEmprestimoServiceOutput simulacao);
}
