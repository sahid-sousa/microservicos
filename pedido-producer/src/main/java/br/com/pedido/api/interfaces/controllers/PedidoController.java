package br.com.pedido.api.interfaces.controllers;


import br.com.pedido.api.infrastructure.broker.PedidoProducer;
import br.com.pedido.api.interfaces.dto.PedidoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class PedidoController {

    PedidoProducer producer;

    public PedidoController(PedidoProducer producer) {
        this.producer = producer;
    }

    @PostMapping("/pedido")
    public ResponseEntity<?> pedido(@RequestBody PedidoDto pedido) {
        log.info(pedido.toString());
        producer.enviar(pedido);
        return ResponseEntity.ok().build();
    }

}
