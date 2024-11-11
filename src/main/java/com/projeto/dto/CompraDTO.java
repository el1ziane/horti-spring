package com.projeto.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.projeto.model.Produto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CompraDTO(
        @JsonProperty("_id") Long id,
        @NotNull @NotBlank String fornecedorCompra,
        Produto produtoCompra,
        @NotNull @Min(1) int quantidadeCompra,
        @NotNull @Min(0) double valorCompra,
        @NotNull @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy") LocalDate dataCompra,
        String status) {

}