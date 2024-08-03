package com.projeto.dto.mapper;

import org.springframework.stereotype.Component;

import com.projeto.dto.FornecedorDTO;
import com.projeto.model.Fornecedor;
@Component
public class FornecedorMapper {
      public FornecedorDTO toDTO(Fornecedor fornecedor){
        if(fornecedor == null){
            return null;
        }
        return new FornecedorDTO(fornecedor.getId(), fornecedor.getFornecedorNome(), fornecedor.getCnpj(), fornecedor.getTelefone(), fornecedor.getEmail());
    }

    public Fornecedor toEntity(FornecedorDTO fornecedorDTO){
        if(fornecedorDTO == null){
            return null;
        }
    
       Fornecedor fornecedor = new Fornecedor();
       if (fornecedorDTO.id() != null){
           fornecedor.setId(fornecedorDTO.id());
       }

       fornecedor.setFornecedorNome(fornecedorDTO.fornecedorNome());
       fornecedor.setCnpj(fornecedorDTO.cnpj());
       fornecedor.setTelefone(fornecedorDTO.telefone());
       fornecedor.setEmail(fornecedorDTO.email());
       return fornecedor;
    }
}

