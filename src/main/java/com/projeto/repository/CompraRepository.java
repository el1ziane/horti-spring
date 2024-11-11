package com.projeto.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.projeto.model.Compra;

public interface CompraRepository extends JpaRepository<Compra, Long> {
  @Query("SELECT SUM(c.valorCompra) FROM Compra c WHERE c.status = :status")
  Double findTotalByStatus(@Param("status") String status);

  List<Compra> findByDataCompraBetween(LocalDate startDate, LocalDate endDate);
}