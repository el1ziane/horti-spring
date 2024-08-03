package com.projeto.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ProducaoDTO(

    @JsonProperty("_id") Long id,
    @NotNull @Min(10) int codigo,
    @NotNull @Min(10) int quantidadeProducao) {
        
    }

