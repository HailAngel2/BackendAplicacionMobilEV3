package com.example.demo.dto;

import java.util.List;
import com.example.demo.model.ENUMTipoDocumento;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class VentaRequestDTO {

    @NotNull(message = "El ID de usuario es requerido")
    private Long idUsuario;

    @NotNull(message = "El tipo de documento es requerido")
    private ENUMTipoDocumento tipoDocumento;

    @NotEmpty(message = "La venta debe contener al menos un detalle de producto")
    private List<LineaVentaDTO> lineas;
}