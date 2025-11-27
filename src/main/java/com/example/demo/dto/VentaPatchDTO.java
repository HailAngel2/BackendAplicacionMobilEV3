package com.example.demo.dto;

import com.example.demo.model.ENUMTipoDocumento;
import lombok.Data;
import java.time.LocalDate;

@Data
public class VentaPatchDTO {
    private ENUMTipoDocumento tipoDocumento;
    private LocalDate fechaVenta; 
    // private String notas; // Se podría añadir cualquier otro campo simple aquí
}