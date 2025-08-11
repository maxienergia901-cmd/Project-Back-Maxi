package com.co.maxi.energia.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Productos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;
    private BigDecimal precio;

    @ManyToMany
    private List<Colores> colores;

    @ManyToOne
    @JoinColumn(name = "presentacion")
    private Presentacion presentacion;

    @ManyToOne
    @JoinColumn(name = "categoria")
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "producto")
    private CodProductos producto;

    @OneToOne
    @JoinColumn(name = "imagen")
    private FileMetadata imagen;
}
