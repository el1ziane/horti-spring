package com.projeto.model;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
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
import jakarta.validation.constraints.NotNull;

@Entity

@SQLDelete(sql = "UPDATE Estimativa SET estado = 'Inativo' WHERE id=?")
//@Where(clause = "estado = 'Ativo'")
@SQLRestriction("estado <> 'Inativo'")
public class Estimativa {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
@JsonProperty("_id")
private Long id;

@ManyToOne 
@JoinColumn(name = "produto_estimativa_id", nullable = false)
private Produto produtoEstimativa;

//@NotNull
//@Min(0)
@Column
private Double pesoEstimativa;

//@NotNull
//@Min(10)
@Column
private Integer caixaEstimativa;

@Column(nullable = false)
@Convert(converter = StatusConverter.class)
private Status tipoEntrada;

@Column(nullable = false)
private Double resultado;

@NotNull
@Column(length = 10, nullable = false)
@Convert(converter = EstadoConverter.class)
private Estado estado = Estado.ATIVO;


    
    public Produto getProdutoEstimativa() {
        return produtoEstimativa;
    }

    public void setProdutoEstimativa(Produto produtoEstimativa) {
        this.produtoEstimativa = produtoEstimativa;
    }

public Long getId() {
    return id;
}

public void setId(Long id) {
    this.id = id;
}

public Double getPesoEstimativa() {
    return pesoEstimativa;
}

public void setPesoEstimativa(Double pesoEstimativa) {
    this.pesoEstimativa = pesoEstimativa;
}

public Integer getCaixaEstimativa() {
    return caixaEstimativa;
}

public void setCaixaEstimativa(Integer caixaEstimativa) {
    this.caixaEstimativa = caixaEstimativa;
}
public Status getTipoEntrada() {
    return tipoEntrada;
}

public void setTipoEntrada(Status tipoEntrada) {
    this.tipoEntrada = tipoEntrada;
}
public double getResultado() {
    return resultado;
}

public void setResultado(double resultado) {
    this.resultado = resultado;
}
public Estado getEstado() {
    return estado;
}

public void setEstado(Estado estado) {
    this.estado = estado;
}

}
