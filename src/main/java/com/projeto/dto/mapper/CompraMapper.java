package com.projeto.dto.mapper;

import org.springframework.stereotype.Component;

import com.projeto.dto.CompraDTO;
import com.projeto.enums.Status;
import com.projeto.model.Compra;

@Component
public class CompraMapper {
    public CompraDTO toDTO(Compra compra){
        if(compra == null){
            return null;
        }
        
        return new CompraDTO(compra.getId(), compra.getFornecedorCompra(), compra.getProdutoCompra(),compra.getQuantidadeCompra(),compra.getValorCompra(),compra.getDataCompra(),compra.getStatus().getValue());
    }

    public Compra toEntity(CompraDTO compraDTO){
        if(compraDTO == null){
            return null;
        }
    
        Compra compra = new Compra();
       if (compraDTO.id() != null){
        compra.setId(compraDTO.id());
       }

       compra.setFornecedorCompra(compraDTO.fornecedorCompra());
       compra.setProdutoCompra(compraDTO.produtoCompra());
       compra.setQuantidadeCompra(compraDTO.quantidadeCompra());
       compra.setValorCompra(compraDTO.valorCompra());
       compra.setDataCompra(compraDTO.dataCompra());
       
       compra.setStatus(convertStatusValue(compraDTO.status()));
       return compra;
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
