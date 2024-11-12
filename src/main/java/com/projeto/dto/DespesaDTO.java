package com.projeto.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DespesaDTO(
        @JsonProperty("_id") Long id,
        @NotBlank String fornecedorDespesa,
        @NotBlank String descricaoDespesa,
        @NotNull @Min(0) double valorDespesa,
        @NotNull @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy") LocalDate dataDespesa,
        String statusDespesa) {

}
