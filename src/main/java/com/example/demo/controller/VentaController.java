package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.VentaPatchDTO;
import com.example.demo.dto.VentaRequestDTO;
import com.example.demo.dto.VentaUpdateEstadoDTO;
import com.example.demo.model.Venta;
import com.example.demo.service.VentaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/ventas")
@Validated
public class VentaController {

    @Autowired
    private VentaService ventaService;
    @PostMapping
    public ResponseEntity<?> registrarVenta(@Valid @RequestBody VentaRequestDTO ventaDTO) {
            Venta nuevaVenta = ventaService.registrarVenta(ventaDTO);
            return new ResponseEntity<>(nuevaVenta, HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Venta> getVentaById(@PathVariable Long id) {
        Venta venta = ventaService.findById(id);
        return new ResponseEntity<>(venta, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<Venta>> getAllVentas(Pageable pageable) {
        Page<Venta> ventas = ventaService.findAll(pageable);
        return new ResponseEntity<>(ventas, HttpStatus.OK);
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<Venta> actualizarEstadoVenta(
            @PathVariable Long id,
            @Valid @RequestBody VentaUpdateEstadoDTO estadoDTO) {
        
        Venta ventaActualizada = ventaService.actualizarEstadoVenta(id, estadoDTO.getNuevoEstado());
        
        return new ResponseEntity<>(ventaActualizada, HttpStatus.OK); 
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Venta> actualizarVentaParcial(
            @PathVariable Long id,
            @RequestBody VentaPatchDTO patchDTO) {
        
        Venta ventaActualizada = ventaService.actualizarMetadatosVenta(id, patchDTO);
        
        return new ResponseEntity<>(ventaActualizada, HttpStatus.OK); 
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteVenta(@PathVariable Long id) {
        ventaService.cancelarVenta(id);
        
        return ResponseEntity.noContent().build();
    }
    

}