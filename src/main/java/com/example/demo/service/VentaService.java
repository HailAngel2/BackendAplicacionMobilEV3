package com.example.demo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.VentaRequestDTO;
import com.example.demo.dto.DetalleVentaDTO;
import com.example.demo.dto.LineaVentaDTO;
import com.example.demo.dto.VentaPatchDTO;
import com.example.demo.exception.RecursoNoEncontradoException;
import com.example.demo.exception.StockException;
import com.example.demo.model.Venta;
import com.example.demo.model.ENUMEstadoVenta;
import com.example.demo.model.LineaVenta;
import com.example.demo.model.Producto;
import com.example.demo.model.Usuario;
import com.example.demo.repository.VentaRepository;
import com.example.demo.repository.LineaVentaRepository;
import com.example.demo.repository.UsuarioRepository;
import lombok.NonNull;


@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository; 
    
    @Autowired
    private ProductoService productoService;
    
    @Autowired
    private LineaVentaRepository lineaVentaRepository;
    
    private static final double PORCENTAJE_IVA = 0.19; 


    @Transactional 
    public Venta registrarVenta(VentaRequestDTO ventaDTO) 
        throws RecursoNoEncontradoException, StockException {

        // --- 1. Validar y recuperar Usuario ---
        Usuario usuario = usuarioRepository.findById(ventaDTO.getIdUsuario())
                                           .orElseThrow(() -> new RecursoNoEncontradoException(
                                               "Usuario no encontrado con ID: " + ventaDTO.getIdUsuario()));

        // --- 2. Inicializar Venta y Folio ---
        Venta nuevaVenta = new Venta();
        nuevaVenta.setUsuario(usuario);
        nuevaVenta.setTipoDocumento(ventaDTO.getTipoDocumento());
        nuevaVenta.setFechaVenta(LocalDate.now());
        nuevaVenta.setFolio(this.generarNuevoFolio());
        nuevaVenta.setEstado(ENUMEstadoVenta.PENDIENTE); // Estado inicial

        List<LineaVenta> lineasVenta = new ArrayList<>();
        double totalNetoAcumulado = 0.0;
        
        // --- 3. Procesar, Validar Stock y Calcular Detalles ---
        for (LineaVentaDTO lineaDTO : ventaDTO.getLineas()) {
        
        // CORRECCIÓN 2: Llama al nuevo método en ProductoService para validar y obtener precio
        Producto producto = productoService.validarStockYObtenerProducto(
                                    lineaDTO.getIdProducto(), 
                                    lineaDTO.getCantidad());
        
        // Crear la NUEVA entidad LineaVenta
        LineaVenta linea = new LineaVenta();
        linea.setProducto(producto);
        linea.setVenta(nuevaVenta);
        linea.setCantidad(lineaDTO.getCantidad());
        
        double precioUnitario = producto.getPrecioUnitario(); 
        linea.setPrecioVentaUnitario(precioUnitario);
        
        double subtotal = precioUnitario * linea.getCantidad();
        linea.setSubtotal(subtotal);
        
        totalNetoAcumulado += subtotal;
        lineasVenta.add(linea);
        
        productoService.descontarStock(producto.getIdProducto(), linea.getCantidad()); 
    }

        double ivaCalculado = totalNetoAcumulado * PORCENTAJE_IVA;
        double totalFinal = totalNetoAcumulado + ivaCalculado;

        nuevaVenta.setDetalles(lineasVenta);
        nuevaVenta.setTotalNeto(totalNetoAcumulado);
        nuevaVenta.setIva(ivaCalculado);
        nuevaVenta.setTotalFinal(totalFinal);
        
        return ventaRepository.save(nuevaVenta);
    }
    
   
    private Long generarNuevoFolio() {
        return ventaRepository.findLastFolio()
                               .map(ultimoFolio -> ultimoFolio + 1)
                               .orElse(1L); 
    }

    @Transactional(readOnly = true)
    public Venta findById(@NonNull Long idVenta) throws RecursoNoEncontradoException {
        return ventaRepository.findByIdWithDetalles(idVenta) 
            .orElseThrow(() -> new RecursoNoEncontradoException("Venta no encontrada con ID: " + idVenta));    }

    @Transactional(readOnly = true)
    public Page<Venta> findAll(@NonNull Pageable pageable) {
        return ventaRepository.findAll(pageable);
    }

    @Transactional
    public void cancelarVenta(@NonNull Long idVenta) {
        Venta venta = ventaRepository.findById(idVenta)
                .orElseThrow(() -> new RecursoNoEncontradoException("Venta no encontrada con ID: " + idVenta));

        // Bucle CORREGIDO para LineaVenta y ProductoService
        for (LineaVenta linea : venta.getDetalles()) {
            
            // Llama al método de reponer stock en ProductoService
            productoService.reponerStock(linea.getProducto().getIdProducto(), linea.getCantidad()); 
        }
        // Nota: CascadeType.ALL en Venta se encargará de borrar las LineaVenta.
        ventaRepository.delete(venta);
    }

    @Transactional
    public Venta actualizarEstadoVenta(@NonNull Long idVenta, ENUMEstadoVenta nuevoEstado) {
        Venta venta = ventaRepository.findById(idVenta)
                .orElseThrow(() -> new RecursoNoEncontradoException("Venta no encontrada con ID: " + idVenta));
        
        // CORRECCIÓN de la Lógica de Cancelación (Usa LineaVenta y ProductoService)
        if (nuevoEstado == ENUMEstadoVenta.CANCELADA && venta.getEstado() != ENUMEstadoVenta.CANCELADA) {
            
            for (LineaVenta linea : venta.getDetalles()) {
                // Llama al método de reponer stock en ProductoService
                productoService.reponerStock(linea.getProducto().getIdProducto(), linea.getCantidad()); 
            }
        }
        
        venta.setEstado(nuevoEstado);
        return ventaRepository.save(venta);
    }

    @Transactional
    public Venta actualizarMetadatosVenta(@NonNull Long idVenta, VentaPatchDTO patchDTO) {
        Venta venta = ventaRepository.findById(idVenta)
                .orElseThrow(() -> new RecursoNoEncontradoException("Venta no encontrada con ID: " + idVenta));

        // Aplicar solo si el campo viene en el DTO
        if (patchDTO.getTipoDocumento() != null) {
            venta.setTipoDocumento(patchDTO.getTipoDocumento());
        }

        if (patchDTO.getFechaVenta() != null) {
            venta.setFechaVenta(patchDTO.getFechaVenta());
        }
        return ventaRepository.save(venta);
    }
    
}