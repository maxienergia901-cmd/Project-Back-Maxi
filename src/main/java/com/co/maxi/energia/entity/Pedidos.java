package com.co.maxi.energia.entity;

import com.co.maxi.energia.dtos.ProductosPedidos;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Pedidos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date fecha;
    private Long estado;
    private BigDecimal precioTotal;
    private String direccionEntrega;
    private String ciudadEntrega;
    private String departamentoEntrega;
    private String observacionEntrega;
    private String nombreEmpresa;
    private String apartamentoEtc;
    private String email;
    private String celular;
    private String nombreUsuario;
    private String apellidoUsuario;

    @ElementCollection
    private List<ProductosPedidos> productos;
}
