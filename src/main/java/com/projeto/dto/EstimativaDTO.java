package com.projeto.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.projeto.model.Produto;

public record EstimativaDTO(
    @JsonProperty("_id") Long id,
    Produto produtoEstimativa,
    Double pesoEstimativa,
    Integer caixaEstimativa,
    Double resultado,
    String tipoEntrada
    )

{
}
