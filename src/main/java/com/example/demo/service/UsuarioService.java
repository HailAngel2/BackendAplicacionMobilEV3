package com.example.demo.service;

import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.exception.RecursoNoEncontradoException;
import com.example.demo.exception.StockException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import lombok.NonNull;
import com.example.demo.dto.RegistroRequestDTO;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired 
    private PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Usuario getUsuarioById(@NonNull Long idUsuario) {
        return usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con ID: " + idUsuario));
    }

    @Transactional
    public Usuario updateUsuario(Long idUsuario, Usuario detallesActualizados) {
        Usuario usuarioExistente = getUsuarioById(idUsuario);

        if (detallesActualizados.getContrasena() != null && !detallesActualizados.getContrasena().isEmpty()) {
            usuarioExistente.setContrasena(detallesActualizados.getContrasena()); 
        } 

        if (detallesActualizados.getUsername() != null) {
            usuarioExistente.setUsername(detallesActualizados.getUsername());
        }
        
        if (detallesActualizados.getDireccion() != null) {
            usuarioExistente.setDireccion(detallesActualizados.getDireccion());
        }

        return usuarioRepository.save(usuarioExistente);
    }

    @Transactional
    public void deleteUsuario(Long idUsuario) {
        Usuario usuario = getUsuarioById(idUsuario);
        usuarioRepository.delete(usuario);
    }
    
    @Transactional
    public Usuario registrarUsuario(RegistroRequestDTO registroDTO) {
        Usuario nuevoUsuario = new Usuario(); 
        nuevoUsuario.setUsername(registroDTO.getUsername());
        nuevoUsuario.setRol(registroDTO.getRol());
        nuevoUsuario.setDireccion(registroDTO.getDireccion());
        nuevoUsuario.setCorreo(registroDTO.getCorreo());
        
        // AQUÍ SÍ ENCRIPTAMOS (Porque es un usuario nuevo y viene en texto plano)
        String hashedPassword = passwordEncoder.encode(registroDTO.getContrasena());
        nuevoUsuario.setContrasena(hashedPassword);

        if (usuarioRepository.findByUsername(nuevoUsuario.getUsername()).isPresent()) {
            throw new StockException("El nombre de usuario '" + nuevoUsuario.getUsername() + "' ya está registrado.");
        }
        return usuarioRepository.save(nuevoUsuario);
    }

    @Transactional
    public void cambiarContrasena(Long idUsuario, String nuevaContrasenaPlana) {
        Usuario usuario = getUsuarioById(idUsuario);
        String hash = passwordEncoder.encode(nuevaContrasenaPlana);
        
        usuario.setContrasena(hash);
        usuarioRepository.save(usuario);
    }
}