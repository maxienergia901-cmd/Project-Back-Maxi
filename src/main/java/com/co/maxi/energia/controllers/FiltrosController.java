package com.co.maxi.energia.controllers;

import com.co.maxi.energia.dtos.Forms;
import com.co.maxi.energia.services.FiltrosService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("maxienergia/api/filtros")
public class FiltrosController {

    private final FiltrosService filtrosService;

    @GetMapping("/obtener/por/producto/{producto}")
    public ResponseEntity<Forms> obtenerPorProducto(@PathVariable Long producto) {
        return filtrosService.obtenerPorProducto(producto);
    }
}
