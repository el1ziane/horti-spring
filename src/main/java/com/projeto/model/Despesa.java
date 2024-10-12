package com.projeto.model;

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
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity

@SQLDelete(sql = "UPDATE Despesa SET estado = 'Inativo' WHERE id=?")
//@Where(clause = "estado = 'Ativo'")
@SQLRestriction("estado <> 'Inativo'")
public class Despesa {
    @Id
@GeneratedValue(strategy = GenerationType.AUTO)
@JsonProperty("_id")
private Long id;

@NotBlank
@Column(length = 100, nullable = false)
private String fornecedorDespesa;

@NotBlank
@Column(length = 100, nullable = false)
private String descricaoDespesa;

@NotNull
@Min(0)
@Column(nullable = false)
private double valorDespesa;

@Column(nullable = false)
@NotNull
 @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
@Pattern(regexp = "\\d{2}-\\d{2}-\\d{4}")
private String dataDespesa;

@Column(nullable = false)
@Convert(converter = StatusConverter.class)
private Status statusDespesa;

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

public String getFornecedorDespesa() {
    return fornecedorDespesa;
    
}

public void setFornecedorDespesa(String fornecedorDespesa) {
    this.fornecedorDespesa = fornecedorDespesa;
}

public String getDescricaoDespesa() {
    return descricaoDespesa;
}

public void setDescricaoDespesa(String descricaoDespesa) {
    this.descricaoDespesa = descricaoDespesa;
}

public double getValorDespesa() {
    return valorDespesa;
}

public void setValorDespesa(double valorDespesa) {
    this.valorDespesa = valorDespesa;
}

public String getDataDespesa() {
    return dataDespesa;
}

public void setDataDespesa(String dataDespesa) {
    this.dataDespesa = dataDespesa;
}

public Status getStatusDespesa() {
    return statusDespesa;
}

public void setStatusDespesa(Status statusDespesa) {
    this.statusDespesa = statusDespesa;
}

public Estado getEstado() {
    return estado;
}

public void setEstado(Estado estado) {
    this.estado = estado;
}

}
