package com.projeto.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.projeto.model.Produto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RecebimentoDTO(
    @JsonProperty("_id") Long id, 
    @NotNull @NotBlank @Size(min = 3, max = 100) String fornecedorRec,
    Produto produto,
    @NotNull @Min(1) int quantidadeRecebimento, 
    @NotNull @Min(0) double valorRecebimento, 
    @Column(nullable = false)
@NotNull @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy") LocalDate dataRecebimento, 
    String status) {

   
}
