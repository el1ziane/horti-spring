package com.projeto.model;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.projeto.enums.Estado;
import com.projeto.enums.EstadoConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
@Entity

@SQLDelete(sql = "UPDATE Estoque SET estado = 'Inativo' WHERE id=?")
//@Where(clause = "estado = 'Ativo'")
@SQLRestriction("estado <> 'Inativo'")
public class Estoque {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
@JsonProperty("_id")
private Long id;


@Min(10)
@Column(nullable = false)
private Long codigoEstoque;



 @ManyToOne
@JoinColumn(name = "produto_id", nullable = false)  
private Produto produtoEstoque;

@Column(nullable = false)
private int quantidadeEstoque;

@Min(0)
@Column(nullable = false)
private double valorUnitarioEstoque;

@Min(0)
@Column(nullable = false)
private double valorTotalEstoque;

@Column(nullable = false)
 @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
@Pattern(regexp = "\\d{2}-\\d{2}-\\d{4}")
private String dataEstoque;

@NotNull
@Column(length = 10, nullable = false)
@Convert(converter = EstadoConverter.class)
private Estado estado = Estado.ATIVO;
 // Getters e setters

 public Long getId() {
    return id;
}

public void setId(Long id) {
    this.id = id;
}

public Long getCodigoEstoque() {
    return codigoEstoque;
}

public void setCodigoEstoque(Long codigoEstoque) {
    this.codigoEstoque = codigoEstoque;
}

public Produto getProdutoEstoque() {
    return produtoEstoque;
}

public void setProdutoEstoque(Produto produtoEstoque) {
    this.produtoEstoque = produtoEstoque;
    recalcularValorTotal(); // Recalcula o valor total sempre que o produto é alterado
}

public int getQuantidadeEstoque() {
    return quantidadeEstoque;
}

public void setQuantidadeEstoque(int quantidadeEstoque) {
    this.quantidadeEstoque = quantidadeEstoque;
    recalcularValorTotal(); // Recalcula o valor total sempre que a quantidade muda
}

public double getValorUnitarioEstoque() {
    return valorUnitarioEstoque;
}

public void setValorUnitarioEstoque(double valorUnitarioEstoque) {
    this.valorUnitarioEstoque = valorUnitarioEstoque;
    recalcularValorTotal(); // Recalcula o valor total sempre que o valor unitário muda
}

public double getValorTotalEstoque() {
    return valorTotalEstoque;
}

public void setValorTotalEstoque(double valorTotalEstoque) {
    this.valorTotalEstoque = valorTotalEstoque;
}


public String getDataEstoque() {
    return dataEstoque;
}

public void setDataEstoque(String dataEstoque) {
    this.dataEstoque = dataEstoque;
}

public Estado getEstado() {
    return estado;
}

public void setEstado(Estado estado) {
    this.estado = estado;
}

// Método para recalcular o valor total do estoque
private void recalcularValorTotal() {
    this.valorTotalEstoque = this.quantidadeEstoque * this.valorUnitarioEstoque;
}
}
