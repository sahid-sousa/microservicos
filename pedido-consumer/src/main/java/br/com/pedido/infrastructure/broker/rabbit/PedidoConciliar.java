package br.com.pedido.infrastructure.broker.rabbit;

import br.com.commons.dto.pedido.PedidoDto;
import br.com.pedido.infrastructure.broker.RpcProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PedidoConciliar implements RpcProducer<PedidoDto> {

    private final RabbitTemplate rabbitTemplate;

    public PedidoConciliar(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void enviar(PedidoDto pedidoDto) {
        log.info(" [P] Publisher sent {} 'Pedido!'", pedidoDto.codigo());
        PedidoDto response = (PedidoDto) rabbitTemplate.convertSendAndReceive(
                RabbitMQConfig.TRANSACAO_REQUEST_EXCHANGE,
                RabbitMQConfig.TRANSACAO_REQUEST_ROUTING_KEY,
                pedidoDto
        );
        log.info(" [P] Publisher received {} 'Pedido!'", response);
    }

}
