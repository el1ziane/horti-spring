package com.projeto.model;

import java.time.LocalDate;

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

@Entity

@SQLDelete(sql = "UPDATE Producao SET estado = 'Inativo' WHERE id=?")
// @Where(clause = "estado = 'Ativo'")
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

    @ManyToOne
    @JoinColumn(name = "produto_producao_id", nullable = false)
    private Produto produtoProducao;

    @Column(nullable = false)
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate dataProducao;

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

    public Produto getProdutoProducao() {
        return produtoProducao;
    }

    public void setProdutoProducao(Produto produtoProducao) {
        this.produtoProducao = produtoProducao;
    }

    public LocalDate getDataProducao() {
        return dataProducao;
    }

    public void setDataProducao(LocalDate dataProducao) {
        this.dataProducao = dataProducao;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

}
