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
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Min;

@Entity
@SQLDelete(sql = "UPDATE Venda SET estado = 'Inativo' WHERE id=?")
@SQLRestriction("estado <> 'Inativo'")
public class Venda {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("_id")
    private Long id;

    @NotNull
    @Min(10)
    @Column(nullable = false)
    private Long codigoVenda;

    @ManyToOne 
    @JoinColumn(name = "produto_venda_id", nullable = false)
    private Produto produtoVenda;  

    @NotNull
    @Min(1)
    @Column(nullable = false)
    private int quantidadeVenda;

    @NotNull
    @Min(1)
    @Column(nullable = false)
    private double valorVenda;

    @Column(nullable = false)
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate dataInicial;

    @Column(nullable = false)
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate dataFinal;

    @Column(length = 20,nullable = false)
    @Convert(converter = StatusConverter.class)
    private Status statusVenda;

    @NotNull
    @Column(length = 10, nullable = false)
    @Convert(converter = EstadoConverter.class)
    private Estado estado = Estado.ATIVO;

    // Construtor sem argumentos
    public Venda() {}

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCodigoVenda() {
        return codigoVenda;
    }

    public void setCodigoVenda(Long codigoVenda) {
        this.codigoVenda = codigoVenda;
    }


    public Produto getProdutoVenda() {
        return produtoVenda;
    }

    public void setProdutoVenda(Produto produtoVenda) {
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

    public LocalDate getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(LocalDate dataInicial) {
        this.dataInicial = dataInicial;
    }

    public LocalDate getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(LocalDate dataFinal) {
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
