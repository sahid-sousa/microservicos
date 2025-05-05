package br.com.pedido.infrastructure.broker.rabbit;

import br.com.commons.dto.pedido.PedidoDto;
import br.com.pedido.application.usecases.AlterarPedido;
import br.com.pedido.infrastructure.broker.RpcProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PedidoConciliar implements RpcProducer<PedidoDto> {

    private final RabbitTemplate rabbitTemplate;
    private final AlterarPedido alterarPedido;

    public PedidoConciliar(RabbitTemplate rabbitTemplate, AlterarPedido alterarPedido) {
        this.rabbitTemplate = rabbitTemplate;
        this.alterarPedido = alterarPedido;
    }

    @Override
    public void enviar(PedidoDto pedidoDto) {
        log.info(" [P] Publisher sent {} 'Pedido!'", pedidoDto.codigo());
        PedidoDto response = (PedidoDto) rabbitTemplate.convertSendAndReceive(
                RabbitMQConfig.TRANSACAO_REQUEST_EXCHANGE,
                RabbitMQConfig.TRANSACAO_REQUEST_ROUTING_KEY,
                pedidoDto
        );
        if (response != null) {
            log.info(" [P] Publisher received {} 'Pedido!'", response);
            alterarPedido.executar(response);
        } else {
            log.info(" [P] Publisher sent {} 'Pedido!' not received", pedidoDto.codigo());
        }
    }


}
