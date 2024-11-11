package com.projeto.model;

import java.time.LocalDate;

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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;

@Entity
//@Table(name = "recebimentos")

@SQLDelete(sql = "UPDATE Compra SET estado = 'Inativo' WHERE id=?")
//@Where(clause = "estado = 'Ativo'")
@SQLRestriction("estado <> 'Inativo'")

public class Compra {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
@JsonProperty("_id")
private Long id;

@NotNull
@NotBlank
@Column(length = 100, nullable = false)
private String fornecedorCompra;

@ManyToOne 
@JoinColumn
private Produto produtoCompra;

@NotNull
@Min(1)
@Column(nullable = false)
private int quantidadeCompra;

@NotNull
@Min(0)
@Column(nullable = false)
private double valorCompra;

@Column(nullable = false)
@NotNull
@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
private LocalDate dataCompra;

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

public String getFornecedorCompra() {
    return fornecedorCompra;
}

public void setFornecedorCompra(String fornecedorCompra) {
    this.fornecedorCompra = fornecedorCompra;
}

public Produto getProdutoCompra() {
    return produtoCompra;
}

public void setProdutoCompra(Produto produtoCompra) {
    this.produtoCompra = produtoCompra;
}

public int getQuantidadeCompra() {
    return quantidadeCompra;
}

public void setQuantidadeCompra(int quantidadeCompra) {
    this.quantidadeCompra = quantidadeCompra;
}

public double getValorCompra() {
    return valorCompra;
}

public void setValorCompra(double valorCompra) {
    this.valorCompra = valorCompra;
}

public LocalDate getDataCompra() {
    return dataCompra;
}

public void setDataCompra(LocalDate dataCompra) {
    this.dataCompra = dataCompra;
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