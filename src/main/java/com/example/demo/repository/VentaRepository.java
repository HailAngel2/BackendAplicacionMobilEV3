package com.example.demo.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Venta;

public interface VentaRepository extends JpaRepository<Venta, Long> {
    Optional<Venta> findByFolio(Long folio);

    @Query("SELECT MAX(v.folio) FROM Venta v")
    Optional<Long> findLastFolio();

    @Query("SELECT v FROM Venta v LEFT JOIN FETCH v.detalles WHERE v.idVenta = :id")
    Optional<Venta> findByIdWithDetalles(@Param("id") Long id);
}