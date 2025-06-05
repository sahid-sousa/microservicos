package br.com.pedido.infrastructure.broker.rabbit;

import br.com.commons.dto.pedido.PedidoDto;
import br.com.pedido.application.usecases.CriarPedido;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PedidoConsumer {

    private final CriarPedido criarPedido;

    public PedidoConsumer(CriarPedido criarPedido) {
        this.criarPedido = criarPedido;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void consumer(PedidoDto pedidoDto) {
        log.info("Pedido recebido");
        criarPedido.executar(pedidoDto);
    }

}
