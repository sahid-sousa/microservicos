package br.com.pedido.adapters.gateway.pagamento;

import br.com.pedido.domain.entities.Pagamento;
import br.com.pedido.domain.entities.Pedido;
import br.com.pedido.infrastructure.database.PagamentoRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PagamentoGatewayImpl implements PagamentoGateway {

    private final PagamentoRepository pagamentoRepository;

    public PagamentoGatewayImpl(PagamentoRepository pagamentoRepository) {
        this.pagamentoRepository = pagamentoRepository;
    }

    @Override
    public Pagamento save(Pagamento pagamento) {
        return pagamentoRepository.save(pagamento);
    }

    @Override
    public Pagamento findById(Long id) {
        return pagamentoRepository.findById(id);
    }

    @Override
    public List<Pagamento> findPagamentoByPedido(Pedido pedido) {
        return pagamentoRepository.findPagamentoByPedido(pedido);
    }

    @Override
    public Optional<Pagamento> findByCartaoAndBandeiraAndNsuAndCodigoAutorizacaoAndParcelas
            (
                    String cartao,
                    String bandeira,
                    Integer nsu,
                    String codigoAutorizacao,
                    Integer parcelas
            ) {
        return pagamentoRepository.findByCartaoAndBandeiraAndNsuAndCodigoAutorizacaoAndParcelas
                (
                        cartao,
                        bandeira,
                        nsu,
                        codigoAutorizacao,
                        parcelas
                );
    }

}
