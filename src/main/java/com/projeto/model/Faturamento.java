package com.projeto.model;

import java.time.LocalDate;

public class Faturamento {
   
    private Long id;

    private String produtoFat;

    private Integer vendaFat = 0;       // Definindo valor inicial
    private Double valorFat = 0.0;      // Definindo valor inicial
    private Integer prodFat = 0;        // Definindo valor inicial
    private Double vcompraFat = 0.0;    // Definindo valor inicial
    private Double lucroFat = 0.0;      // Definindo valor inicial
    private Integer perdaFat = 0;       // Definindo valor inicial

    private LocalDate dataFat;

    public LocalDate getDataFat() {
        return dataFat;
    }

    public void setDataFat(LocalDate dataFat) {
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
