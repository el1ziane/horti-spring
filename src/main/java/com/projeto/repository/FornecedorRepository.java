package com.projeto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.model.Fornecedor;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long>{
    
    
}
