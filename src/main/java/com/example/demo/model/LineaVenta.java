package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class LineaVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLinea;
    
    @ManyToOne
    @JoinColumn(name = "idVenta")
    @JsonIgnore
    private Venta venta;
    
    @ManyToOne
    @JoinColumn(name = "idProducto")
    private Producto producto;
    
    @Column(nullable = false)
    private int cantidad; // Cantidad vendida
    
    @Column(nullable = false)
    private double precioVentaUnitario; // Precio al momento de la venta

    private double subtotal;
}