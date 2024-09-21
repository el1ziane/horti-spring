package com.projeto.dto.mapper;

import org.springframework.stereotype.Component;

import com.projeto.dto.VendaDTO;
import com.projeto.enums.Status;
import com.projeto.model.Venda;

@Component
public class VendaMapper {
    public VendaDTO toDTO(Venda venda){
        if(venda == null){
            return null;
        }
        
        return new VendaDTO(venda.getId(), venda.getCodigoVenda(), venda.getProdutoVenda(),venda.getQuantidadeVenda(),venda.getValorVenda(), venda.getDataInicial(), venda.getDataFinal(), venda.getStatusVenda().getValue());
    }

    public Venda toEntity(VendaDTO vendaDTO){
        if(vendaDTO == null){
            return null;
        }
    
       Venda venda = new Venda();
       if (vendaDTO.id() != null){
        venda.setId(vendaDTO.id());
       }

       venda.setCodigoVenda(vendaDTO.codigoVenda());
       venda.setProdutoVenda(vendaDTO.produtoVenda());
       venda.setQuantidadeVenda(vendaDTO.quantidadeVenda());
       venda.setValorVenda(vendaDTO.valorVenda());
       venda.setDataInicial(vendaDTO.dataInicial());
       venda.setDataFinal(vendaDTO.dataFinal());
       
       venda.setStatusVenda(convertStatusValue(vendaDTO.statusVenda()));
       return venda;
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
