package com.projeto.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.projeto.model.Produto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
public record EstoqueDTO(
    @JsonProperty("_id") Long id,
    @Min(10) Long codigoEstoque,
    Produto produtoEstoque,
    int quantidadeEstoque,
    @Min(0) double valorUnitarioEstoque,
    @Min(0) double valorTotalEstoque,
    @Column(nullable = false)
@NotNull
@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy") LocalDate dataEstoque
) {
} 