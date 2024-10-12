package com.projeto.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record VendaDTO(
@JsonProperty("_id") Long id,
@NotNull @Min(10) Long codigoVenda,
@NotNull @NotBlank  String produtoVenda,
@NotNull @Min(1) int quantidadeVenda,
@NotNull @Min(0) double valorVenda,
//@NotNull  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy") @Pattern(regexp = "\\d{2}-\\d{2}-\\d{4}") String periodoVenda,
@NotNull  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy") @Pattern(regexp = "\\d{2}-\\d{2}-\\d{4}") String dataInicial,
@NotNull  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy") @Pattern(regexp = "\\d{2}-\\d{2}-\\d{4}") String dataFinal,
String statusVenda){

}
    

