package br.com.transacao.infrastructure.broker;

import br.com.commons.dto.pedido.PedidoDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class PedidoConciliarConsumer {

    @RabbitListener(queues = RabbitMQConfig.TRANSACAO_REQUEST_QUEUE)
    public PedidoDto pedidoConciliar(PedidoDto pedidoDto) {
        return pedidoDto;
    }

}
