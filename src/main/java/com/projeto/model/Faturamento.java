package com.projeto.model;

import java.time.LocalDate;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public class Faturamento {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
@JsonProperty("_id")
private Long id;

private String produtoFat;



    private int vendaFat;

   

    private Double valorFat; 



    private int prodFat;



    private Double vcompraFat; 


    private Double lucroFat; 

 

    private Integer perdaFat;

    private LocalDate dataFat;

    public LocalDate getDataFat(){
        return dataFat;
    }

    public void setDataFat(LocalDate dataFat){
        this.dataFat = dataFat;
    }

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public String getProdutoFat() {
        return produtoFat;
    }

    public void setProdutoFat(String produtoFat) {
        this.produtoFat = produtoFat;
    }

    public int getVendaFat() {
        return vendaFat;
    }

    public void setVendaFat(int vendaFat) {
        this.vendaFat = vendaFat;
    }

    public Double getValorFat() {
        return valorFat;
    }

    public void setValorFat(Double valorFat) {
        this.valorFat = valorFat;
    }

    public int getProdFat() {
        return prodFat;
    }

    public void setProdFat(int prodFat) {
        this.prodFat = prodFat;
    }

    public Double getVcompraFat() {
        return vcompraFat;
    }

    public void setVcompraFat(Double vcompraFat) {
        this.vcompraFat = vcompraFat;
    }

    public Double getLucroFat() {
        return lucroFat;
    }

    public void setLucroFat(Double lucroFat) {
        this.lucroFat = lucroFat;
    }

    public Integer getPerdaFat() {
        return perdaFat;
    }

    public void setPerdaFat(Integer perdaFat) {
        this.perdaFat = perdaFat;
    }

    
}
