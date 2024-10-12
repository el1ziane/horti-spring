package com.projeto.dto.mapper;

import org.springframework.stereotype.Component;

import com.projeto.dto.DespesaDTO;
import com.projeto.enums.Status;
import com.projeto.model.Despesa;

@Component
public class DespesaMapper {
    public DespesaDTO toDTO(Despesa despesa){
        if(despesa == null){
            return null;
        }
        
        return new DespesaDTO(despesa.getId(), despesa.getFornecedorDespesa(), despesa.getDescricaoDespesa(),despesa.getValorDespesa(),despesa.getDataDespesa(), despesa.getStatusDespesa().getValue());
    }

    public Despesa toEntity(DespesaDTO despesaDTO){
        if(despesaDTO == null){
            return null;
        }
    
       Despesa despesa = new Despesa();
       if (despesaDTO.id() != null){
        despesa.setId(despesaDTO.id());
       }

       despesa.setFornecedorDespesa(despesaDTO.fornecedorDespesa());
       despesa.setDescricaoDespesa(despesaDTO.descricaoDespesa());
       despesa.setValorDespesa(despesaDTO.valorDespesa());
       despesa.setDataDespesa(despesaDTO.dataDespesa());
       despesa.setStatusDespesa(convertStatusValue(despesaDTO.statusDespesa()));
       return despesa;
    }
    public Status convertStatusValue(String value) {
        if (value == null) {
            return null;
        }
        return switch (value) {
            case "Pago" -> Status.PAGO;
            case "Pendente" -> Status.PENDENTE;    
            default -> throw new IllegalArgumentException("Status inválido: " + value);
        };
    }
    
}
