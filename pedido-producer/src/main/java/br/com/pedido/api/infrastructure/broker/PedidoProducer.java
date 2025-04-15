package br.com.pedido.api.infrastructure.broker;

import br.com.pedido.api.interfaces.dto.PedidoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PedidoProducer {

    private final RabbitTemplate rabbitTemplate;

    public PedidoProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviar(PedidoDto pedido) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME,  RabbitMQConfig.ROUTING_KEY, pedido);
        log.info("Pedido enviado: {}", pedido);
    }
}
