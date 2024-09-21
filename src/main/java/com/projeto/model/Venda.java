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
//@Table(name = "vendas")

@SQLDelete(sql = "UPDATE Venda SET estado = 'Inativo' WHERE id=?")
//@Where(clause = "estado = 'Ativo'")
@SQLRestriction("estado <> 'Inativo'")

public class Venda {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
@JsonProperty("_id")
private Long id;

@NotNull
@Min(10)
@Column(nullable = false)
private int codigoVenda;

@NotNull
@NotBlank
@Size(min = 3, max =100)
@Column(length = 100, nullable = false)
private String produtoVenda;

@NotNull
@Min(1)
@Column(nullable = false)
private int quantidadeVenda;

@NotNull
@Min(0)
@Column(nullable = false)
private double valorVenda;



@Column(nullable = false)
@NotNull
 @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
@Pattern(regexp = "\\d{2}-\\d{2}-\\d{4}")
private String dataInicial;

@Column(nullable = false)
@NotNull
 @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
@Pattern(regexp = "\\d{2}-\\d{2}-\\d{4}")
private String dataFinal;

//@Pattern(regexp = "Recebido|Pendente")
@Column(nullable = false)
@Convert(converter = StatusConverter.class)
private Status statusVenda;

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

public int getCodigoVenda() {
    return codigoVenda;
}

public void setCodigoVenda(int codigoVenda)  {
    this.codigoVenda = codigoVenda;
}

public String getProdutoVenda() {
    return produtoVenda;
}

public void setProdutoVenda(String produtoVenda) {
    this.produtoVenda = produtoVenda;
}

public int getQuantidadeVenda() {
    return quantidadeVenda;
}

public void setQuantidadeVenda(int quantidadeVenda) {
    this.quantidadeVenda = quantidadeVenda;
}

public double getValorVenda() {
    return valorVenda;
}

public void setValorVenda(double valorVenda) {
    this.valorVenda = valorVenda;
}



public String getDataInicial() {
    return dataInicial;
}

public void setDataInicial(String dataInicial) {
    this.dataInicial = dataInicial;
}

public String getDataFinal() {
    return dataFinal;
}

public void setDataFinal(String dataFinal) {
    this.dataFinal = dataFinal;
}

public Status getStatusVenda() {
    return statusVenda;
}

public void setStatusVenda(Status statusVenda) {
    this.statusVenda = statusVenda;
}

public Estado getEstado() {
    return estado;
}

public void setEstado(Estado estado) {
    this.estado = estado;
}


}
