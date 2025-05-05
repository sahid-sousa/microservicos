package br.com.transacao.infrastructure.broker.rabbit;

import br.com.commons.dto.pedido.PedidoDto;
import br.com.commons.dto.transacao.TransacaoDto;
import br.com.commons.dto.venda.VendaDetailDto;
import br.com.transacao.infrastructure.broker.Producer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PedidoConciliarConsumer {

    private final VendaConciliar vendaConciliar;
    private final Producer<TransacaoDto> transacaoProducer;

    public PedidoConciliarConsumer(VendaConciliar vendaConciliar, TransacaoProducer transacaoProducer) {
        this.vendaConciliar = vendaConciliar;
        this.transacaoProducer = transacaoProducer;
    }

    @RabbitListener(queues = RabbitMQConfig.TRANSACAO_REQUEST_QUEUE)
    public PedidoDto pedidoConciliar(PedidoDto pedidoDto) {
        return getPedidoDto(pedidoDto);
    }

    private PedidoDto getPedidoDto(PedidoDto pedidoDto) {
        List<VendaDetailDto> vendas = vendaConciliar.enviar(pedidoDto);
        if (!vendas.isEmpty()) {
            log.info("Pedido: {} conciliado com sucesso", pedidoDto.uuid());
            enviar(pedidoDto, vendas);
            return getDto(pedidoDto);
        }
        log.info("Pedido: {} nao conciliado ", pedidoDto.uuid());
        return pedidoDto;
    }

    private void enviar(PedidoDto pedidoDto, List<VendaDetailDto> vendas) {
        for (VendaDetailDto venda : vendas) {
            transacaoProducer.enviar(
                    new TransacaoDto(
                            pedidoDto.loja().codigo(),
                            pedidoDto.codigo(),
                            venda.uuid(),
                            pedidoDto.data(),
                            venda.parcelas(),
                            venda.valorTransacao(),
                            venda.taxaTransacao(),
                            venda.tipoTransacao(),
                            venda.cartao(),
                            venda.codigoAutorizacao(),
                            venda.nsu(),
                            venda.bandeira()
                    )
            );
        }
    }

    private static PedidoDto getDto(PedidoDto pedidoDto) {
        return new PedidoDto(
                pedidoDto.uuid(),
                pedidoDto.codigo(),
                pedidoDto.valor(),
                pedidoDto.data(),
                pedidoDto.faturado(),
                true,
                pedidoDto.loja(),
                pedidoDto.pagamentos()
        );
    }


}
