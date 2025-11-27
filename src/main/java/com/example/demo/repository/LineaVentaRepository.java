package com.example.demo.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import com.example.demo.model.LineaVenta;

public interface LineaVentaRepository extends JpaRepository<LineaVenta, Long> {
    List<LineaVenta> findByVentaIdVenta(Long idVenta);
}