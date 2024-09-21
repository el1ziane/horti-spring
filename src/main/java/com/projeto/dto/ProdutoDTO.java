package com.projeto.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProdutoDTO(

    @JsonProperty("_id") Long id,
    @NotNull @Min(10) Long codigo,
    @NotNull @NotBlank @Size(min = 3, max =100) String produtoNome,
    @NotNull @Min(0) double peso,
    @NotNull @Min(10) int quantidadeProduto,
    @NotNull @Min(0) double valorProduto) {
        
    }

