package br.com.transacao.infrastructure.broker.rabbit;

import br.com.commons.dto.transacao.TransacaoDto;
import br.com.transacao.infrastructure.broker.Producer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TransacaoProducer implements Producer<TransacaoDto> {

    private final RabbitTemplate rabbitTemplate;

    public TransacaoProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void enviar(TransacaoDto transacaoDto) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, transacaoDto);
    }

}
