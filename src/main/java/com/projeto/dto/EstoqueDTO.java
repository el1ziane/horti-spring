package com.projeto.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.projeto.model.Produto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
public record EstoqueDTO(
    @JsonProperty("_id") Long id,
    @Min(10) Long codigoEstoque,
    Produto produtoEstoque,
    int quantidadeEstoque,
    @Min(0) double valorUnitarioEstoque,
    @Min(0) double valorTotalEstoque,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy") @Pattern(regexp = "\\d{2}-\\d{2}-\\d{4}") String dataEstoque
) {
} 