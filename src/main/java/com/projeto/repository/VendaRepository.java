package com.projeto.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.projeto.model.Venda;


public interface VendaRepository extends JpaRepository<Venda, Long>{
  List<Venda> findByDataInicialBetween(LocalDate startDate, LocalDate endDate);
    
    
} 
