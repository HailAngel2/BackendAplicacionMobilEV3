package com.example.demo.dto;
import com.example.demo.model.ENUMRolUsuario;
import lombok.Data;

@Data
public class RegistroRequestDTO {
    private String username;
    private String contrasena;
    private ENUMRolUsuario rol;
    private String direccion;
    private String correo;
}
//