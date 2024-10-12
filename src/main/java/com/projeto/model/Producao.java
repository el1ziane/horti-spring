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
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity

@SQLDelete(sql = "UPDATE Producao SET estado = 'Inativo' WHERE id=?")
//@Where(clause = "estado = 'Ativo'")
@SQLRestriction("estado <> 'Inativo'")
public class Producao {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
@JsonProperty("_id")
private Long id;

@NotNull
@Min(10)
@Column(nullable = false)
private Long codigoProducao;

@NotNull
@Column(nullable = false)
private int quantidadeProducao;

@NotNull
@NotBlank
@Column(length = 100, nullable = false)
private String produtoProducao;

@Column(nullable = false)
@NotNull
@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
@Pattern(regexp = "\\d{2}-\\d{2}-\\d{4}")
private String dataProducao;

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

public Long getCodigoProducao() {
    return codigoProducao;
}

public void setCodigoProducao(Long codigoProducao) {
    this.codigoProducao = codigoProducao;
}

public int getQuantidadeProducao() {
    return quantidadeProducao;
}

public void setQuantidadeProducao(int quantidadeProducao) {
    this.quantidadeProducao = quantidadeProducao;
}

public String getProdutoProducao() {
    return produtoProducao;
}

public void setProdutoProducao(String produtoProducao) {
    this.produtoProducao = produtoProducao;
}

public String getDataProducao() {
    return dataProducao;
}

public void setDataProducao(String dataProducao) {
    this.dataProducao = dataProducao;
}

public Estado getEstado() {
    return estado;
}

public void setEstado(Estado estado) {
    this.estado = estado;
}


}
