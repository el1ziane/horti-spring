package com.projeto.model;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.projeto.enums.Estado;
import com.projeto.enums.EstadoConverter;
import com.projeto.enums.Status;
import com.projeto.enums.StatusConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;



@Entity
//@Table(name = "recebimentos")

@SQLDelete(sql = "UPDATE Recebimento SET estado = 'Inativo' WHERE id=?")
//@Where(clause = "estado = 'Ativo'")
@SQLRestriction("estado <> 'Inativo'")

public class Recebimento {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
@JsonProperty("_id")
private Long id;

@NotNull
@NotBlank
@Size(min = 3, max =100)
@Column(length = 100, nullable = false)
private String fornecedor;

@NotNull
@NotBlank
@Size(min = 3, max =100)
@Column(length = 100, nullable = false)
private String produto;

@NotNull
@Min(1)
@Column(nullable = false)
private int quantidadeRecebimento;

@NotNull
@Min(0)
@Column(nullable = false)
private double valorRecebimento;

@Column(nullable = false)
@NotNull
 @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
@Pattern(regexp = "\\d{2}-\\d{2}-\\d{4}")
private String dataRecebimento;

//@Pattern(regexp = "Pago|A faturar")
@Column(nullable = false)
@Convert(converter = StatusConverter.class)
private Status status;

@NotNull
@Column(length = 10, nullable = false)
@Convert(converter = EstadoConverter.class)
private Estado estado = Estado.ATIVO;

public Long getId() {
    return id;
}

public void setId(Long id) {
    this.id = id;
}

public String getFornecedor() {
    return fornecedor;
}

public void setFornecedor(String fornecedor) {
    this.fornecedor = fornecedor;
}

public String getProduto() {
    return produto;
}

public void setProduto(String produto) {
    this.produto = produto;
}

public int getQuantidadeRecebimento() {
    return quantidadeRecebimento;
}

public void setQuantidadeRecebimento(int quantidadeRecebimento) {
    this.quantidadeRecebimento = quantidadeRecebimento;
}

public double getValorRecebimento() {
    return valorRecebimento;
}

public void setValorRecebimento(double valorRecebimento) {
    this.valorRecebimento = valorRecebimento;
}

public String getDataRecebimento() {
    return dataRecebimento;
}

public void setDataRecebimento(String dataRecebimento) {
    this.dataRecebimento = dataRecebimento;
}

public Status getStatus() {
    return status;
}

public void setStatus(Status status) {
    this.status = status;
}

public Estado getEstado() {
    return estado;
}

public void setEstado(Estado estado) {
    this.estado = estado;
}


}
