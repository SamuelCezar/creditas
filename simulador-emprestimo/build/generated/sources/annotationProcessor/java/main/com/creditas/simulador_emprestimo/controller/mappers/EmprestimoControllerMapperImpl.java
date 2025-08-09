package com.creditas.simulador_emprestimo.controller.mappers;

import com.creditas.simulador_emprestimo.controller.data.request.SimulacaoRequest;
import com.creditas.simulador_emprestimo.controller.data.response.SimulacaoResponse;
import com.creditas.simulador_emprestimo.service.data.input.SimulacaoEmprestimoServiceInput;
import com.creditas.simulador_emprestimo.service.data.output.SimulacaoEmprestimoServiceOutput;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-09T10:26:14-0300",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.14.3.jar, environment: Java 21.0.7 (Microsoft)"
)
@Component
public class EmprestimoControllerMapperImpl implements EmprestimoControllerMapper {

    @Override
    public SimulacaoEmprestimoServiceInput toSimulacaoServiceInput(SimulacaoRequest simulacaoRequest) {
        if ( simulacaoRequest == null ) {
            return null;
        }

        SimulacaoEmprestimoServiceInput simulacaoEmprestimoServiceInput = new SimulacaoEmprestimoServiceInput();

        simulacaoEmprestimoServiceInput.setValorEmprestimo( simulacaoRequest.getValorEmprestimo() );
        simulacaoEmprestimoServiceInput.setDataNascimento( simulacaoRequest.getDataNascimento() );
        simulacaoEmprestimoServiceInput.setPrazoMeses( simulacaoRequest.getPrazoMeses() );

        return simulacaoEmprestimoServiceInput;
    }

    @Override
    public SimulacaoResponse toSimulacaoEmprestimoResponse(SimulacaoEmprestimoServiceOutput simulacao) {
        if ( simulacao == null ) {
            return null;
        }

        SimulacaoResponse simulacaoResponse = new SimulacaoResponse();

        simulacaoResponse.setValorEmprestimo( simulacao.getValorEmprestimo() );
        simulacaoResponse.setPrazoMeses( simulacao.getPrazoMeses() );
        simulacaoResponse.setValorTotalAPagar( simulacao.getValorTotalAPagar() );
        simulacaoResponse.setParcelaMensal( simulacao.getParcelaMensal() );
        simulacaoResponse.setTotalJurosPagos( simulacao.getTotalJurosPagos() );
        simulacaoResponse.setJurosAnual( simulacao.getJurosAnual() );
        simulacaoResponse.setJurosMensal( simulacao.getJurosMensal() );

        return simulacaoResponse;
    }
}
