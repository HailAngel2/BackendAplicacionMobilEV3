package com.example.demo.controller;

import com.example.demo.dto.ProductoPatchDTO;
import com.example.demo.model.Producto;
import com.example.demo.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductoController {
    
    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<Producto>> getAll() {
        return ResponseEntity.ok(productoService.getAllProductos());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Producto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.getProductoById(id));
    }

    @PostMapping
    public ResponseEntity<Producto> create(@RequestBody Producto entity) {
        Producto nuevoProducto = productoService.createOrUpdateProducto(entity);
        return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> update(@PathVariable Long id, @RequestBody Producto details) {
        productoService.getProductoById(id); 
        
        details.setIdProducto(id);
        
        Producto actualizado = productoService.createOrUpdateProducto(details);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productoService.deleteProducto(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Producto>> searchByNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(productoService.findByNombreContaining(nombre));
    }

    @GetMapping("/categoria/{nombreCategoria}")
    public ResponseEntity<List<Producto>> searchByCategoria(@PathVariable String nombreCategoria) {
        return ResponseEntity.ok(productoService.findByCategoria(nombreCategoria));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Producto> updateParcial(@PathVariable Long id, 
                                                  @RequestBody ProductoPatchDTO patchDTO) {
        
        Producto productoActualizado = productoService.updateParcialProducto(id, patchDTO);
        
        return ResponseEntity.ok(productoActualizado);
    }
}