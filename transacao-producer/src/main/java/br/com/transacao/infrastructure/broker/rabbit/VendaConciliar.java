package br.com.transacao.infrastructure.broker.rabbit;

import br.com.commons.dto.pedido.PedidoDto;
import br.com.commons.dto.venda.VendaDetailDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class VendaConciliar {

    private final RabbitTemplate rabbitTemplate;

    public VendaConciliar(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public List<VendaDetailDto> enviar(PedidoDto pedidoDto) {
        log.info(" [P] Publisher sent {} 'Pedido!'", pedidoDto.codigo());
        var response = rabbitTemplate.convertSendAndReceive(
                RabbitMQConfig.VENDA_REQUEST_EXCHANGE,
                RabbitMQConfig.VENDA_REQUEST_ROUTING_KEY,
                pedidoDto
        );
        if (response instanceof List<?> vendasResponse) {
            return vendasResponse.stream()
                    .filter(VendaDetailDto.class::isInstance)
                    .map(VendaDetailDto.class::cast)
                    .toList();
        } else {
            throw new IllegalStateException("Resposta inesperada do consumidor");
        }
    }

}
