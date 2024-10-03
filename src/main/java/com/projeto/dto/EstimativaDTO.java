package com.projeto.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record EstimativaDTO(
    @JsonProperty("_id") Long id,
    @NotNull @NotBlank @Size(min = 3, max =100) String produtoEstimativa,
    Double pesoEstimativa,
    Integer caixaEstimativa,
    Double resultado,
    String tipoEntrada
    )

{
}
