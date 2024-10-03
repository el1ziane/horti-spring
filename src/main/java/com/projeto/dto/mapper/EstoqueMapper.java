package com.projeto.dto.mapper;

import org.springframework.stereotype.Component;

import com.projeto.dto.EstoqueDTO;
import com.projeto.model.Estoque;

@Component
public class EstoqueMapper {
     public EstoqueDTO toDTO(Estoque estoque){
        if(estoque == null){
            return null;
        }
        
        return new EstoqueDTO(estoque.getId(), estoque.getCodigoEstoque(),estoque.getProdutoEstoque(),estoque.getQuantidadeEstoque(),estoque.getValorUnitarioEstoque(),estoque.getValorTotalEstoque(),estoque.getDataEstoque());
    }

    public Estoque toEntity(EstoqueDTO estoqueDTO){
        if(estoqueDTO == null){
            return null;
        }
    
       Estoque estoque = new Estoque();
       if (estoqueDTO.id() != null){
        estoque.setId(estoqueDTO.id());
       }

       estoque.setCodigoEstoque(estoqueDTO.codigoEstoque());
       estoque.setProdutoEstoque(estoqueDTO.produtoEstoque());
       estoque.setQuantidadeEstoque(estoqueDTO.quantidadeEstoque());
       estoque.setValorUnitarioEstoque(estoqueDTO.valorUnitarioEstoque());
       estoque.setValorTotalEstoque(estoqueDTO.valorTotalEstoque());
       estoque.setDataEstoque(estoqueDTO.dataEstoque());
       return estoque;
    }
}

