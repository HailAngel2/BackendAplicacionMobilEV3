package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoPatchDTO {
    
    // Campos simples a actualizar (usan tipos envoltorio para ser null)
    private String nombreProducto;
    private Double precioUnitario; 
    private Integer cantidad; // El stock
    private String urlImagen;
    
    private Long idNuevaCategoria; 
}