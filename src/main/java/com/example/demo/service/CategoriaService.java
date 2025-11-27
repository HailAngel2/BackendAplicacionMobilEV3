package com.example.demo.service;

import com.example.demo.model.Categoria;
import com.example.demo.repository.CategoriaRepository;
import com.example.demo.exception.RecursoNoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import lombok.NonNull;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional(readOnly = true)
    public List<Categoria> getAllCategorias() {
        return categoriaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Categoria getCategoriaById(@NonNull Long idCategoria) {
        return categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new RecursoNoEncontradoException("Categoría no encontrada con ID: " + idCategoria));
    }

    @Transactional
    public Categoria createOrUpdateCategoria(@NonNull Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @Transactional
    public void deleteCategoria(Long idCategoria) {
        Categoria categoria = getCategoriaById(idCategoria);
        categoriaRepository.delete(categoria);
    }
}