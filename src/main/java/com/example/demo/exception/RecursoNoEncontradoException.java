package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecursoNoEncontradoException extends RuntimeException {

    // Constructor que acepta un mensaje de error
    public RecursoNoEncontradoException(String message) {
        super(message);
    }
    
    // Opcional: Constructor para incluir la causa raíz
    public RecursoNoEncontradoException(String message, Throwable cause) {
        super(message, cause);
    }
}