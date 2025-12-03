package com.example.demo.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.time.LocalDate;
import jakarta.persistence.EnumType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString(exclude = {"usuario", "detalles"})
@EqualsAndHashCode(callSuper = false, exclude = {"usuario", "detalles"})

public class Venta {

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVenta;

    @Column(name = "fechaVenta")
    private LocalDate fechaVenta;

    @Column(unique = true)
    private Long folio; // El número legal de la boleta (ej:0001)

    @Enumerated(EnumType.STRING)
    private ENUMTipoDocumento tipoDocumento;

    @ManyToOne
    @JoinColumn(name = "idUsuario")
    @JsonBackReference
    @JsonIgnore
    private Usuario usuario;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<LineaVenta> detalles; 

    @Enumerated(EnumType.STRING)
    private ENUMEstadoVenta estado;

    private double totalNeto;
    private double iva;
    private double totalFinal;

}