package com.projeto.model;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.projeto.enums.Estado;
import com.projeto.enums.EstadoConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity

@SQLDelete(sql = "UPDATE Produto SET estado = 'Inativo' WHERE id=?")
//@Where(clause = "estado = 'Ativo'")
@SQLRestriction("estado <> 'Inativo'")
public class Produto {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
@JsonProperty("_id")
private Long id;

@NotNull
@Min(10)
@Column(nullable = false)
private Long codigo;

@NotNull
@NotBlank
@Size(min = 3, max =100)
@Column(length = 100, nullable = false)
private String produtoNome;

@NotNull
@Min(0)
@Column(nullable = false)
private double peso;

@NotNull
@Min(10)
@Column(nullable = false)
private int quantidadeProduto;

@NotNull
@Min(0)
@Column(nullable = false)
private double valorProduto;

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

public Long getCodigo() {
    return codigo;
}

public void setCodigo(Long codigo) {
    this.codigo = codigo;
}

public String getProdutoNome() {
    return produtoNome;
}

public void setProdutoNome(String produtoNome) {
    this.produtoNome = produtoNome;
}

public double getPeso() {
    return peso;
}

public void setPeso(double peso) {
    this.peso = peso;
}

public int getQuantidadeProduto() {
    return quantidadeProduto;
}

public void setQuantidadeProduto(int quantidadeProduto) {
    this.quantidadeProduto = quantidadeProduto;
}

public double getValorProduto() {
    return valorProduto;
}

public void setValorProduto(double valorProduto) {
    this.valorProduto = valorProduto;
}

public Estado getEstado() {
    return estado;
}

public void setEstado(Estado estado) {
    this.estado = estado;
}

}
