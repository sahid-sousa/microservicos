package br.com.pedido.application.services;

import br.com.commons.dto.pedido.PedidoDto;
import br.com.pedido.adapters.gateway.pedido.PedidoGateway;
import br.com.pedido.application.usecases.AlterarPedido;
import br.com.pedido.domain.entities.Pedido;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class AlterarPedidoImpl implements AlterarPedido {

    private final PedidoGateway pedidoGateway;

    public AlterarPedidoImpl(PedidoGateway pedidoGateway) {
        this.pedidoGateway = pedidoGateway;
    }

    @Override
    public void executar(PedidoDto pedidoDto) {
        Optional<Pedido> pedido = pedidoGateway.findByUuid(pedidoDto.uuid());
        if (pedido.isPresent()) {
            if (pedidoDto.conciliado()) {
                log.info("Conciliando: {} Pedido: {}", true, pedidoDto.uuid());
                Pedido alterado = pedido.get();
                alterado.setConciliado(true);
                pedidoGateway.save(alterado);
            } else {
                log.info("Pedido: {} nao conciliado: {}", pedidoDto.uuid(), false);
            }
        } else {
            log.info("Pedido {} nao encontrado", pedidoDto.uuid());
        }
    }
}
