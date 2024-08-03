package com.projeto.dto.mapper;

import org.springframework.stereotype.Component;
import com.projeto.dto.RecebimentoDTO;
import com.projeto.enums.Status;
import com.projeto.model.Recebimento;

@Component
public class RecebimentoMapper {
    public RecebimentoDTO toDTO(Recebimento recebimento){
        if(recebimento == null){
            return null;
        }
        
        return new RecebimentoDTO(recebimento.getId(), recebimento.getFornecedorRec(), recebimento.getProduto(),recebimento.getQuantidadeRecebimento(),recebimento.getValorRecebimento(),recebimento.getDataRecebimento(),recebimento.getStatus().getValue());
    }

    public Recebimento toEntity(RecebimentoDTO recebimentoDTO){
        if(recebimentoDTO == null){
            return null;
        }
    
       Recebimento recebimento = new Recebimento();
       if (recebimentoDTO.id() != null){
        recebimento.setId(recebimentoDTO.id());
       }

       recebimento.setFornecedorRec(recebimentoDTO.fornecedorRec());
       recebimento.setProduto(recebimentoDTO.produto());
       recebimento.setQuantidadeRecebimento(recebimentoDTO.quantidadeRecebimento());
       recebimento.setValorRecebimento(recebimentoDTO.valorRecebimento());
       recebimento.setDataRecebimento(recebimentoDTO.dataRecebimento());
       
       recebimento.setStatus(convertStatusValue(recebimentoDTO.status()));
       return recebimento;
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
