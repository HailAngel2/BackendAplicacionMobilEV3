package com.example.demo.dto;
import lombok.Data;

@Data
public class LoginRequestDTO {
    private String username; 
    private String contrasena;

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
}