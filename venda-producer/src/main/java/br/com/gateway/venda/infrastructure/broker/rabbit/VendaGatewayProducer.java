package br.com.gateway.venda.infrastructure.broker.rabbit;

import br.com.commons.dto.venda.VendaDetailDto;
import br.com.gateway.venda.infrastructure.broker.Producer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class VendaGatewayProducer implements Producer<VendaDetailDto> {

    private final RabbitTemplate rabbitTemplate;

    public VendaGatewayProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviar(VendaDetailDto venda) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME,  RabbitMQConfig.ROUTING_KEY, venda);
        log.info("Venda enviado: {}", venda);
    }

}
