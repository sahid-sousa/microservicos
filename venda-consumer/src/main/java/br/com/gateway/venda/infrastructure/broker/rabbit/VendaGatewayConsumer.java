package br.com.gateway.venda.infrastructure.broker.rabbit;

import br.com.commons.dto.venda.VendaDetailDto;
import br.com.gateway.venda.application.usecase.CriarVenda;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class VendaGatewayConsumer {

    private final CriarVenda criarVenda;

    public VendaGatewayConsumer(CriarVenda criarVenda) {
        this.criarVenda = criarVenda;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void consumer(VendaDetailDto vendaDetailDto) {
        log.info("Venda recebida");
        criarVenda.executar(vendaDetailDto);
        log.info("Venda criada com sucesso");
    }

}
