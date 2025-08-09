package com.creditas.simulador_emprestimo.service.mappers;

import com.creditas.simulador_emprestimo.service.data.input.SimulacaoEmprestimoServiceInput;
import com.creditas.simulador_emprestimo.service.data.output.SimulacaoEmprestimoServiceOutput;
import java.math.BigDecimal;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-09T10:26:13-0300",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.14.3.jar, environment: Java 21.0.7 (Microsoft)"
)
@Component
public class EmprestimoServiceMapperImpl implements EmprestimoServiceMapper {

    @Override
    public SimulacaoEmprestimoServiceOutput toSimulacaoEmprestimoServiceOutput(SimulacaoEmprestimoServiceInput input, BigDecimal jurosAnual, BigDecimal jurosMensal, BigDecimal valorTotalAPagar, BigDecimal totalJurosPagos, BigDecimal parcelaMensal) {
        if ( input == null && jurosAnual == null && jurosMensal == null && valorTotalAPagar == null && totalJurosPagos == null && parcelaMensal == null ) {
            return null;
        }

        SimulacaoEmprestimoServiceOutput simulacaoEmprestimoServiceOutput = new SimulacaoEmprestimoServiceOutput();

        if ( input != null ) {
            simulacaoEmprestimoServiceOutput.setPrazoMeses( input.getPrazoMeses() );
            simulacaoEmprestimoServiceOutput.setValorEmprestimo( input.getValorEmprestimo() );
        }
        simulacaoEmprestimoServiceOutput.setJurosAnual( jurosAnual );
        simulacaoEmprestimoServiceOutput.setJurosMensal( jurosMensal );
        simulacaoEmprestimoServiceOutput.setValorTotalAPagar( valorTotalAPagar );
        simulacaoEmprestimoServiceOutput.setTotalJurosPagos( totalJurosPagos );
        simulacaoEmprestimoServiceOutput.setParcelaMensal( parcelaMensal );

        return simulacaoEmprestimoServiceOutput;
    }
}
