package br.com.gateway.venda.infrastructure.broker.rabbit;

import br.com.commons.dto.pedido.PedidoDto;
import br.com.commons.dto.venda.VendaDetailDto;
import br.com.gateway.venda.application.usecase.BuscarVenda;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class BuscarVendaGatewayConsumer {

    BuscarVenda buscarVenda;

    public BuscarVendaGatewayConsumer(BuscarVenda buscarVenda) {
        this.buscarVenda = buscarVenda;
    }

    @RabbitListener(queues = RabbitMQConfig.VENDA_REQUEST_QUEUE)
    List<VendaDetailDto> consumer(PedidoDto pedidoDto) {
        log.info("Buscando venda para o pedido: {}", pedidoDto.uuid());
        return buscarVenda.executar(pedidoDto);
    }

}
