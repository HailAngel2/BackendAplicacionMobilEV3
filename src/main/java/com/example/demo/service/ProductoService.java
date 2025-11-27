package com.example.demo.service;

import com.example.demo.model.Categoria;
import com.example.demo.model.Producto;
import com.example.demo.repository.ProductoRepository;
import com.example.demo.repository.CategoriaRepository;
import com.example.demo.dto.ProductoPatchDTO;
import com.example.demo.exception.RecursoNoEncontradoException;
import com.example.demo.exception.StockException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

import lombok.NonNull;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    
    @Autowired
    private CategoriaRepository categoriaRepository;
    
    @Transactional
    public Producto createOrUpdateProducto(Producto producto) {
        if (producto.getCategoria() != null && producto.getCategoria().getIdCategoria() != null) {
            categoriaRepository.findById(producto.getCategoria().getIdCategoria())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                    "No se puede guardar el producto. Categoría no encontrada con ID: " + producto.getCategoria().getIdCategoria()));
        }
        
        return productoRepository.save(producto);
    }

    @Transactional(readOnly = true)
    public List<Producto> getAllProductos() {
        return productoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Producto getProductoById(@NonNull Long idProducto) {
        return productoRepository.findById(idProducto)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado con ID: " + idProducto));
    }
    
    @Transactional
    public void deleteProducto(Long idProducto) {
        Producto producto = getProductoById(idProducto);
        productoRepository.delete(producto);
    }

    @Transactional(readOnly = true)
    public List<Producto> findByNombreContaining(String nombre) {
        return productoRepository.findByNombreProductoContainingIgnoreCase(nombre);
    }

    @Transactional(readOnly = true)
    public List<Producto> findByCategoria(String nombreCategoria) {
        return productoRepository.findByCategoriaNombreCategoria(nombreCategoria);
    }

    @Transactional
    public Producto updateParcialProducto(@NonNull Long idProducto, ProductoPatchDTO patchDTO) {
        Producto productoExistente = getProductoById(idProducto);
        Optional.ofNullable(patchDTO.getNombreProducto())
            .ifPresent(productoExistente::setNombreProducto);
            
        Optional.ofNullable(patchDTO.getPrecioUnitario())
            .ifPresent(productoExistente::setPrecioUnitario);

        Optional.ofNullable(patchDTO.getCantidad())
            .ifPresent(productoExistente::setCantidad);
            
        Optional.ofNullable(patchDTO.getUrlImagen())
            .ifPresent(productoExistente::setUrlImagen);
        

        if (patchDTO.getIdNuevaCategoria() != null) {
            Categoria nuevaCategoria = categoriaRepository.findById(patchDTO.getIdNuevaCategoria())
                .orElseThrow(() -> new RecursoNoEncontradoException("Categoría para la actualización no encontrada."));
            productoExistente.setCategoria(nuevaCategoria);
        }
        return productoRepository.save(productoExistente);
    }

    @Transactional(readOnly = true)
    public Producto validarStockYObtenerProducto(Long idProducto, int cantidadSolicitada) 
    throws RecursoNoEncontradoException, StockException {
    
    Producto producto = productoRepository.findById(idProducto)
        .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado con ID: " + idProducto));

    if (producto.getCantidad() < cantidadSolicitada) {
        throw new StockException("Stock insuficiente para el producto ID: " + idProducto);
    }

    return producto;
}

@Transactional
public void descontarStock(Long idProducto, int cantidadVendida) {
    Producto producto = getProductoById(idProducto);
    producto.setCantidad(producto.getCantidad() - cantidadVendida);
    productoRepository.save(producto);
}

@Transactional
public void reponerStock(Long idProducto, int cantidadAReponer) {
    Producto producto = getProductoById(idProducto);
    producto.setCantidad(producto.getCantidad() + cantidadAReponer);
    productoRepository.save(producto);
}
}
