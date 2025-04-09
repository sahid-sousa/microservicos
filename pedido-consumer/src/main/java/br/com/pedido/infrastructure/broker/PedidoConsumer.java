package br.com.pedido.infrastructure.broker;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import br.com.pedido.interfaces.dto.PedidoDto;

@Slf4j
@Service
public class PedidoConsumer {

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void consumer(PedidoDto pedido) {
        log.info("Consumer received");
    }

}
