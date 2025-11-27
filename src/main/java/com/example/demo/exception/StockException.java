package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class StockException extends RuntimeException {

    // Constructor que acepta un mensaje de error
    public StockException(String message) {
        super(message);
    }
    
    // Opcional: Constructor para incluir la causa raíz
    public StockException(String message, Throwable cause) {
        super(message, cause);
    }
}