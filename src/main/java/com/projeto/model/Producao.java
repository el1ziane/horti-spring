package com.projeto.model;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity

@SQLDelete(sql = "UPDATE Produto SET estado = 'Inativo' WHERE id=?")
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
private int codigo;

@NotNull
@Min(10)
@Column(nullable = false)
private int quantidadeProducao;

public Long getId() {
    return id;
}

public void setId(Long id) {
    this.id = id;
}

public int getCodigo() {
    return codigo;
}

public void setCodigo(int codigo) {
    this.codigo = codigo;
}

public int getQuantidadeProducao() {
    return quantidadeProducao;
}

public void setQuantidadeProducao(int quantidadeProducao) {
    this.quantidadeProducao = quantidadeProducao;
}


}
