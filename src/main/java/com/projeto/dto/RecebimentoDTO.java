package com.projeto.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RecebimentoDTO(
    @JsonProperty("_id") Long id, 
    @NotNull @NotBlank @Size(min = 3, max = 100) String fornecedor,
    @NotNull @NotBlank @Size(min = 3, max = 100) String produto,
    @NotNull @Min(1) int quantidadeRecebimento, 
    @NotNull @Min(0) double valorRecebimento, 
    @NotNull  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy") @Pattern(regexp = "\\d{2}-\\d{2}-\\d{4}") String dataRecebimento, 
    String status) {

   
}
