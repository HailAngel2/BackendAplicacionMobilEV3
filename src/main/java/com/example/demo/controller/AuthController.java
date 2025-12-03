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

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequestDTO loginRequest) {
        
        // 1. Ver qué usuario nos piden
        System.out.println("--- INTENTO DE LOGIN ---");
        System.out.println("Usuario recibido: " + loginRequest.getUsername());
        
        Usuario usuario = usuarioRepository.findByUsername(loginRequest.getUsername())
            .orElseThrow(() -> new RecursoNoEncontradoException("Credenciales inválidas."));

        // 2. Ver qué contraseñas tenemos (¡Cuidado! esto mostrará la pass en logs, bórralo después)
        System.out.println("Pass ingresada (App): " + loginRequest.getContrasena());
        System.out.println("Hash en BD: " + usuario.getContrasena());

        // 3. Ver qué dice el comparador
        boolean matches = passwordEncoder.matches(
            loginRequest.getContrasena(), 
            usuario.getContrasena()
        );
        System.out.println("¿Coinciden?: " + matches);
        System.out.println("------------------------");

        if (matches) {
            return ResponseEntity.ok(Map.of(
                "message", "Login exitoso",
                "id", usuario.getIdUsuario(),
                "username", usuario.getUsername(),
                "rol", usuario.getRol().toString()
            ));
        } else {
            return new ResponseEntity<>("Credenciales inválidas.", HttpStatus.UNAUTHORIZED);
        }
    }
    
    
    @PostMapping("/login/a")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequestDTO loginRequest, int a) {
        
        Usuario usuario = usuarioRepository.findByUsername(loginRequest.getUsername())
            .orElseThrow(() -> new RecursoNoEncontradoException("Credenciales inválidas."));

        boolean matches = passwordEncoder.matches(
            loginRequest.getContrasena(), 
            usuario.getContrasena()
        );

        if (matches) {
            return ResponseEntity.ok(Map.of(
                "message", "Login exitoso",
                "id", usuario.getIdUsuario(),
                "username", usuario.getUsername(),
                "rol", usuario.getRol().toString()
            ));
        } else {
            return new ResponseEntity<>("Credenciales inválidas.", HttpStatus.UNAUTHORIZED);
        }
    }
}