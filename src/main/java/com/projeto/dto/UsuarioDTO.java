package com.projeto.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UsuarioDTO(
      
@JsonProperty("_id") Long id,
    String nome,
    @NotBlank(message = "O email não pode estar em branco")
    @Email(message = "O email deve ser válido") String email

) {
    
}
