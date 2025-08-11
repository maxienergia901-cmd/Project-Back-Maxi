package com.co.maxi.energia.services.impl;

import com.co.maxi.energia.Repository.CarritoRepository;
import com.co.maxi.energia.Repository.PedidosRepository;
import com.co.maxi.energia.dtos.Contactar;
import com.co.maxi.energia.dtos.ProductosPedidos;
import com.co.maxi.energia.entity.Pedidos;
import com.co.maxi.energia.services.PedidosService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidosServiceImpl implements PedidosService {

    private final PedidosRepository repository;

    private final CarritoRepository carritoRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String email;

    @Override
    public List<Pedidos> obtenerTodos() {
        return repository.findAll();
    }

    @Override
    public Page<Pedidos> obtenerPorFiltro(Pageable pageable, Date fechaDesde, Date fechaHasta, Long estado) {

        Specification<Pedidos> spec = Specification.where(null);

        if (fechaDesde != null && fechaHasta != null) {
            spec = spec.and((root, query, cb) ->
                    cb.between(root.get("fecha"), fechaDesde, fechaHasta));
        } else if (fechaDesde != null) {
            spec = spec.and((root, query, cb) ->
                    cb.greaterThanOrEqualTo(root.get("fecha"), fechaDesde));
        } else if (fechaHasta != null) {
            spec = spec.and((root, query, cb) ->
                    cb.lessThanOrEqualTo(root.get("fecha"), fechaHasta));
        }

        if (estado != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("estado"), estado));
        }

        return repository.findAll(spec, pageable);
    }

    @Override
    public ResponseEntity<Pedidos> obtenerPorId(Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public Pedidos crear(Pedidos pedido) {
        BigDecimal total = BigDecimal.ZERO;

        for (ProductosPedidos producto : pedido.getProductos()) {
            // Multiplicamos por la cantidad
            BigDecimal subtotal = producto.getPrecio()
                    .multiply(BigDecimal.valueOf(producto.getCantidad()));

            total = total.add(subtotal);

            if (carritoRepository.existsById(producto.getIdCarrito())) {
                carritoRepository.deleteById(producto.getIdCarrito());
            }
        }

        pedido.setPrecioTotal(total);
        Pedidos responsePedido = repository.save(pedido);

        String cuerpoVenta = "Se creo una orden de compra con el ID: " + responsePedido.getId()
                + " para el cliente con email: " + responsePedido.getEmail() + " con numero de wssp: "
                + responsePedido.getCelular() + " con entrega a: " + responsePedido.getDireccionEntrega()
                + " por un valor de: $" + responsePedido.getPrecioTotal();
        String asuntoVenta = "Nueva orden de compra creada con ID: " + responsePedido.getId();

        String cuerpoCliente = "Tu orden ha sido creada con exito, " +
                "Estaremos revisando el pedido y nos contactaremos contigo lo antes posible para confirmar todo, " +
                "Gracias por confiar en MaxiEnergia!!";
        String asuntoCliente = "Orden confirmada, MaxiEnergia";

        enviarCorreo(cuerpoVenta, asuntoVenta, email);
        enviarCorreo(cuerpoCliente, asuntoCliente, responsePedido.getEmail());

        return responsePedido;
    }

    @Override
    public Contactar contactar(Contactar contacto) {

        String cuerpoNegocio = "Un cliente desea que lo contactes: " + contacto.getNombre()
                + " con email: " + contacto.getEmail() + " con numero de wssp: "
                + contacto.getCelular() + ", Dejo el siguiente mensaje: " + contacto.getMensaje();
        String asuntoNegocio = "Contactar al cliente " + contacto.getNombre();

        String cuerpoCliente = "Hemos recibido tu mensaje y te estaremos contactando pronto," +
                " Gracias por confiar en MaxiEnergia!!";
        String asuntoCliente = "Hemos recibido tu mensaje, MaxiEnergia";

        enviarCorreo(cuerpoNegocio, asuntoNegocio, email);
        enviarCorreo(cuerpoCliente, asuntoCliente, contacto.getEmail());

        return contacto;
    }

    @Override
    public ResponseEntity<Pedidos> actualizar(Long id, Pedidos datos) {
        return repository.findById(id)
                .map(pedido -> {
                    pedido.setEstado(datos.getEstado());
                    return ResponseEntity.ok(repository.save(pedido));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Void> eliminar(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    public void enviarCorreo(String cuerpo, String asunto, String emailTo) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(emailTo);
        mensaje.setSubject(asunto);
        mensaje.setText(cuerpo);
        mensaje.setFrom(email);

        mailSender.send(mensaje);
    }
}
