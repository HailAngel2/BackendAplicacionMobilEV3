package com.example.demo.controller;

import com.example.demo.dto.PasswordUpdateDTO;
import com.example.demo.dto.RegistroRequestDTO;
import com.example.demo.model.Usuario;
import com.example.demo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PatchMapping("/{id}/password")
    public ResponseEntity<?> updatePassword(@PathVariable Long id, @RequestBody PasswordUpdateDTO passwordDTO) {
        
        // 1. Buscar usuario
        Usuario usuario = usuarioService.getUsuarioById(id);
        
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }

        // 2. Encriptar y actualizar contraseña
        usuario.setContrasena(passwordEncoder.encode(passwordDTO.getNuevoPassword()));
        
        // 3. Guardar cambios
        usuarioService.updateUsuario(id, usuario);

        return ResponseEntity.ok().body("{\"message\": \"Contraseña actualizada correctamente\"}");
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> getAll() {
        return ResponseEntity.ok(usuarioService.getAllUsuarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getById(@PathVariable Long id) {
        // En una aplicación real, se usaría un DTO para no exponer la contraseña.
        return ResponseEntity.ok(usuarioService.getUsuarioById(id));
    }

    @PostMapping
    public ResponseEntity<Usuario> create(@RequestBody RegistroRequestDTO registroDTO) {
        Usuario nuevoUsuario = usuarioService.registrarUsuario(registroDTO); 
        return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> update(@PathVariable Long id, @RequestBody Usuario details) {
        details.setIdUsuario(id); 
        Usuario actualizado = usuarioService.updateUsuario(id, details);
        
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        usuarioService.deleteUsuario(id);
        return ResponseEntity.noContent().build();
    }
}