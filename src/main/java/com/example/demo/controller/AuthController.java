package com.example.demo.controller;

import com.example.demo.dto.LoginRequestDTO;
import com.example.demo.dto.RecuperacionDTO;
import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.exception.RecursoNoEncontradoException;
import com.example.demo.service.EmailService;

import java.util.Map;
import java.util.Random;

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

    @Autowired
    private EmailService emailService;
    
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequestDTO loginRequest, int a) {
        
        Usuario usuario = usuarioRepository.findByUsername(loginRequest.getUsername())
            .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado."));

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
            return new ResponseEntity<>("Contraseña incorrecta.", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/solicitar-codigo")
    public ResponseEntity<?> solicitarCodigo(@RequestBody Map<String, String> payload) {
        String correo = payload.get("correo");
        Usuario usuario = usuarioRepository.findByCorreo(correo).orElse(null);

        if (usuario != null) {
            String codigo = String.format("%06d", new Random().nextInt(999999));
            usuario.setCodigoRecuperacion(codigo);
            usuarioRepository.save(usuario);
            String mensaje = "Hola " + usuario.getUsername() + ",\n\n" +
                             "Tu código de recuperación es: " + codigo + "\n\n" +
                             "Úsalo en la aplicación para restablecer tu contraseña.";
            try {
                emailService.enviarCorreo(correo, "Código de Recuperación - EV3", mensaje);
                return ResponseEntity.ok(Map.of("message", "Código enviado al correo"));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error enviando correo");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Correo no encontrado");
    }

    @PostMapping("/restablecer-password")
    public ResponseEntity<?> restablecerPassword(@RequestBody RecuperacionDTO dto) {
        Usuario usuario = usuarioRepository.findByCorreo(dto.getCorreo()).orElse(null);

        if (usuario != null) {
            if (usuario.getCodigoRecuperacion() != null && usuario.getCodigoRecuperacion().equals(dto.getCodigo())) {
                usuario.setContrasena(passwordEncoder.encode(dto.getNuevaContrasena()));
                usuario.setCodigoRecuperacion(null); 
                usuarioRepository.save(usuario);
                return ResponseEntity.ok(Map.of("message", "Contraseña actualizada exitosamente"));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Código incorrecto");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
    }
}