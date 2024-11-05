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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity

@SQLDelete(sql = "UPDATE Fornecedor SET estado = 'Inativo' WHERE id=?")
//@Where(clause = "estado = 'Ativo'")
@SQLRestriction("estado <> 'Inativo'")
public class Fornecedor {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
@JsonProperty("_id")
private Long id;

@NotNull
@NotBlank
@Column(length = 100, nullable = false)
private String fornecedorNome;

@NotNull
@NotBlank
@Column(length = 14, nullable = false)
private String cnpj;


@NotNull
@NotBlank
@Size(min = 10, max = 15)
@Column(length = 15, nullable = false)
private String telefone;


@NotNull
@NotBlank
@Column(length = 100, nullable = false)
private String email;


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


public String getFornecedorNome() {
    return fornecedorNome;
}

public void setFornecedorNome(String fornecedorNome) {
    this.fornecedorNome = fornecedorNome;
}

public String getCnpj() {
    return cnpj;
}

public void setCnpj (String cnpj) {
    this.cnpj = cnpj;
   
}

public String getTelefone() {
    return telefone;
}

public void setTelefone (String telefone) {
    this.telefone = telefone;
   
}

public String getEmail() {
    return email;
}

public void setEmail (String email) {
    this.email = email;
}


public Estado getEstado() {
    return estado;
}

public void setEstado(Estado estado) {
    this.estado = estado;
}

}
