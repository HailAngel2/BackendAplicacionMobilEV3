package com.example.demo.dto;

import com.example.demo.model.ENUMEstadoVenta;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VentaUpdateEstadoDTO {
    
    @NotNull(message = "El nuevo estado de la venta es obligatorio.")
    private ENUMEstadoVenta nuevoEstado;
    
}