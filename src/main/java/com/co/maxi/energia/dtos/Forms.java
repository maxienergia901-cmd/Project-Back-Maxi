package com.co.maxi.energia.dtos;

import com.co.maxi.energia.entity.Colores;
import com.co.maxi.energia.entity.Presentacion;
import com.co.maxi.energia.entity.Categoria;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Forms {
    private List<Categoria> categorias;
    private List<Presentacion> presentacion;
    private List<Colores> colores;
}