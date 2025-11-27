package com.example.demo.dto;

import lombok.Data;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Data
public class LineaVentaDTO {
    @NotNull(message = "El ID de producto es requerido")
    private Long idProducto;

    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private int cantidad;
}