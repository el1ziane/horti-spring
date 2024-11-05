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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity

@SQLDelete(sql = "UPDATE Estimativa SET estado = 'Inativo' WHERE id=?")
//@Where(clause = "estado = 'Ativo'")
@SQLRestriction("estado <> 'Inativo'")
public class Estimativa {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
@JsonProperty("_id")
private Long id;

@NotNull
@NotBlank
@Size(min = 3, max =100)
@Column(length = 100, nullable = false)
private String produtoEstimativa;

//@NotNull
//@Min(0)
@Column(nullable = false)
private Double pesoEstimativa;

//@NotNull
//@Min(10)
@Column(nullable = false)
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

@ManyToOne // ou @OneToOne dependendo da necessidade
    @JoinColumn(name = "produto_id", nullable = false) // chave estrangeira para produto
    private Produto produto;  // Associa a estimativa ao produto

    // Getters e setters...
    
    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

public Long getId() {
    return id;
}

public void setId(Long id) {
    this.id = id;
}

public String getProdutoEstimativa() {
    return produtoEstimativa;
}

public void setProdutoEstimativa(String produtoEstimativa) {
    this.produtoEstimativa = produtoEstimativa;
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
