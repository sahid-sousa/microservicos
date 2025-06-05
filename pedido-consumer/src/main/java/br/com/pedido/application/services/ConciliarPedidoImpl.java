package br.com.pedido.application.services;

import br.com.commons.dto.pedido.LojaDto;
import br.com.commons.dto.pedido.PagamentoDto;
import br.com.commons.dto.pedido.PedidoDto;
import br.com.pedido.adapters.gateway.pagamento.PagamentoGateway;
import br.com.pedido.adapters.gateway.pedido.PedidoGateway;
import br.com.pedido.application.usecases.ConciliarPedido;
import br.com.pedido.domain.entities.Loja;
import br.com.pedido.domain.entities.Pagamento;
import br.com.pedido.domain.entities.Pedido;
import br.com.pedido.infrastructure.broker.RpcProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ConciliarPedidoImpl implements ConciliarPedido {

    private final RpcProducer<PedidoDto> producer;
    private final PedidoGateway pedidoGateway;
    private final PagamentoGateway pagamentoGateway;

    public ConciliarPedidoImpl(RpcProducer<PedidoDto> producer, PedidoGateway pedidoGateway, PagamentoGateway pagamentoGateway) {
        this.producer = producer;
        this.pedidoGateway = pedidoGateway;
        this.pagamentoGateway = pagamentoGateway;
    }

    @Override
    public void executar() {
        log.info("Executando conciliar pedido");
        Pageable pageable = PageRequest.of(0, 10000);
        List<Long> pedidos = pedidoGateway.findAllStatus(true, false, pageable);

        for (Long id : pedidos) {
            Pedido pedido = pedidoGateway.findById(id);
            PedidoDto pedidoDto = convertFrom(pedido);
            conciliar(pedidoDto);
        }
    }

    private PedidoDto convertFrom(Pedido pedido) {
        Loja loja = pedido.getLoja();
        LojaDto lojaDto = new LojaDto(loja.getUuid(),loja.getCodigo(), loja.getCnpj());
        List<PagamentoDto> pagamentosDto = getPagamentoDtos(pedido);
        return new PedidoDto(
                pedido.getUuid(),
                pedido.getCodigo(),
                pedido.getValor(),
                pedido.getData(),
                pedido.getFaturado(),
                pedido.getConciliado(),
                lojaDto,
                pagamentosDto
        );
    }

    private List<PagamentoDto> getPagamentoDtos(Pedido pedido) {
        List<Pagamento> pagamentos = pagamentoGateway.findPagamentoByPedido(pedido);
        List<PagamentoDto> pagamentosDto = new ArrayList<>();
        for(Pagamento pagamento : pagamentos) {
            pagamentosDto.add(
                    new PagamentoDto(
                            pagamento.getUuid(),
                            pagamento.getTipo(),
                            pagamento.getTipoTransacao(),
                            pagamento.getParcelas(),
                            pagamento.getCartao(),
                            pagamento.getCodigoAutorizacao(),
                            pagamento.getNsu(),
                            pagamento.getBandeira(),
                            pagamento.getValor()

                    )
            );
        }
        return pagamentosDto;
    }

    private void conciliar(PedidoDto pedidoDto) {
        producer.enviar(pedidoDto);
    }
}
