package com.projeto.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.model.Producao;

public interface ProducaoRepository extends JpaRepository<Producao, Long>{

    List<Producao> findByDataProducaoBetween(LocalDate withDayOfMonth, LocalDate withDayOfMonth2);
    
}
