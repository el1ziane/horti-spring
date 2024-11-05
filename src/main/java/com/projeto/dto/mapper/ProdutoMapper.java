package com.projeto.dto.mapper;

import org.springframework.stereotype.Component;
import com.projeto.dto.ProdutoDTO;
import com.projeto.model.Produto;

@Component
public class ProdutoMapper {
      public ProdutoDTO toDTO(Produto produto){
        if(produto == null){
            return null;
        }
        
        return new ProdutoDTO(produto.getId(), produto.getCodigo(), produto.getProdutoNome(),produto.getPeso(),produto.getQuantidadeProduto(), produto.getQuantidadePorCaixa(),produto.getValorProduto());
    }

    public Produto toEntity(ProdutoDTO produtoDTO){
        if(produtoDTO == null){
            return null;
        }
    
       Produto produto = new Produto();
       if (produtoDTO.id() != null){
        produto.setId(produtoDTO.id());
       }

       produto.setCodigo(produtoDTO.codigo());
       produto.setProdutoNome(produtoDTO.produtoNome());
       produto.setPeso(produtoDTO.peso());
       produto.setQuantidadeProduto(produtoDTO.quantidadeProduto());
       produto.setValorProduto(produtoDTO.valorProduto());
       produto.setQuantidadePorCaixa(produtoDTO.quantidadePorCaixa());
       return produto;
    }
}

