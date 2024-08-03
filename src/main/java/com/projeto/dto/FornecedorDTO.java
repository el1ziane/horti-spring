package com.projeto.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public record FornecedorDTO(
    
    @JsonProperty("_id") Long id,
    @NotNull @NotBlank @Size(min = 3, max =100) String fornecedorNome,
    @NotNull @NotBlank @Size(min = 3, max =14) String cnpj,
    @NotNull @NotBlank @Size(min = 3, max =10) String telefone,
    @NotNull @NotBlank @Size(min = 3, max =100) String email) {
        
    }
  
    

