package com.projeto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.model.Producao;

public interface ProducaoRepository extends JpaRepository<Producao, Long>{
    
}
