package com.projeto.dto.mapper;

import org.springframework.stereotype.Component;

import com.projeto.dto.ProducaoDTO;
import com.projeto.model.Producao;

@Component
public class ProducaoMapper {
    public ProducaoDTO toDTO(Producao producao){
      if(producao == null){
          return null;
      }
      
      return new ProducaoDTO(producao.getId(), producao.getCodigoProducao(), producao.getQuantidadeProducao(), producao.getProdutoProducao(), producao.getDataProducao());
  }

  public Producao toEntity(ProducaoDTO producaoDTO){
      if(producaoDTO == null){
          return null;
      }
  
     Producao producao = new Producao();
     if (producaoDTO.id() != null){
      producao.setId(producaoDTO.id());
     }

     producao.setCodigoProducao(producaoDTO.codigoProducao());
     producao.setQuantidadeProducao(producaoDTO.quantidadeProducao());
     
     return producao;
  }
}
