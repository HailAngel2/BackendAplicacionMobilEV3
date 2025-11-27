package com.example.demo.controller;

import com.example.demo.dto.LoginRequestDTO;
import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.exception.RecursoNoEncontradoException;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    // NOTA: ASUMIMOS que UsuarioRepository tiene el método findByUsername
    //       (Si no existe, el proyecto fallará al compilar)
    
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequestDTO loginRequest) {
        
        // 1. Buscar al usuario por username
        Usuario usuario = usuarioRepository.findByUsername(loginRequest.getUsername())
            .orElseThrow(() -> new RecursoNoEncontradoException("Credenciales inválidas.")); // Error 404/401

        // 2. Comparar la contraseña (texto plano vs. hash de la BD)
        boolean matches = passwordEncoder.matches(
            loginRequest.getContrasena(), 
            usuario.getContrasena() // Contraseña hasheada de la BD
        );

        if (matches) {
            // 3. Éxito: Devolver la información necesaria al frontend
            
            // Creamos un mapa o DTO de respuesta para NO exponer el hash de la contraseña
            // En una aplicación real, se devolvería un JWT aquí.
            return ResponseEntity.ok(Map.of(
                "message", "Login exitoso",
                "id", usuario.getIdUsuario(),
                "username", usuario.getUsername(),
                "rol", usuario.getRol().toString()
            ));
        } else {
            // 4. Fallo: Devolver error 401 Unauthorized
            return new ResponseEntity<>("Credenciales inválidas.", HttpStatus.UNAUTHORIZED);
        }
    }
}