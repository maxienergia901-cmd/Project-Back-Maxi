package com.co.maxi.energia.controllers;

import com.co.maxi.energia.dtos.Contactar;
import com.co.maxi.energia.entity.Pedidos;
import com.co.maxi.energia.services.PedidosService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("maxienergia/api/pedidos")
public class PedidosController {

    private final PedidosService service;

    @GetMapping("/obtener/todos")
    public List<Pedidos> obtenerTodos() {
        return service.obtenerTodos();
    }

    @GetMapping("/obtener/por/filtro")
    public Page<Pedidos> obtenerPorProducto(
                                              @RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size,
                                              @RequestParam(required = false)
                                              @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaDesde,
                                              @RequestParam(required = false)
                                              @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaHasta,
                                              @RequestParam(required = false) Long estado) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        return service.obtenerPorFiltro(pageable, fechaDesde, fechaHasta, estado);
    }

    @GetMapping("/obtener/por/id/{id}")
    public ResponseEntity<Pedidos> obtenerPorId(@PathVariable Long id) {
        return service.obtenerPorId(id);
    }

    @PostMapping
    public Pedidos crear(@RequestBody Pedidos pedido) {
        return service.crear(pedido);
    }

    @PostMapping("/contacto")
    public Contactar contactar(@RequestBody Contactar contacto) {
        return service.contactar(contacto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pedidos> actualizar(@PathVariable Long id, @RequestBody Pedidos datos) {
        return service.actualizar(id,datos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        return service.eliminar(id);
    }
}
