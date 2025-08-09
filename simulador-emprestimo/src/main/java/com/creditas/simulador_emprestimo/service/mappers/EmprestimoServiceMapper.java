package com.creditas.simulador_emprestimo.service.mappers;

import com.creditas.simulador_emprestimo.configs.mappers.MapperStructConfig;
import com.creditas.simulador_emprestimo.service.data.input.SimulacaoEmprestimoServiceInput;
import com.creditas.simulador_emprestimo.service.data.output.SimulacaoEmprestimoServiceOutput;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;

@Mapper(config = MapperStructConfig.class)
public interface EmprestimoServiceMapper {

    @Mapping(source = "input.prazoMeses", target = "prazoMeses")
    @Mapping(source = "input.valorEmprestimo", target = "valorEmprestimo")
    SimulacaoEmprestimoServiceOutput toSimulacaoEmprestimoServiceOutput(
            SimulacaoEmprestimoServiceInput input,
            BigDecimal jurosAnual,
            BigDecimal jurosMensal,
            BigDecimal valorTotalAPagar,
            BigDecimal totalJurosPagos,
            BigDecimal parcelaMensal);
}
