package com.co.maxi.energia.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Carrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String usuarioId;
    private Long cantidad;
    private String color;
    private String nombre;
    private String nombreColor;
    private Long idProducto;
    private BigDecimal precio;
    private String imagen;
    private String presentacion;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
