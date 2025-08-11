package com.co.maxi.energia.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contactar {
    private String nombre;
    private String celular;
    private String email;
    private String mensaje;
}
