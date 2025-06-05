package br.com.pedido.adapters.gateway.pagamento;

import br.com.pedido.domain.entities.Pagamento;
import br.com.pedido.domain.entities.Pedido;

import java.util.List;
import java.util.Optional;

public interface PagamentoGateway {

    Pagamento save(Pagamento pagamento);
    Pagamento findById(Long id);
    List<Pagamento> findPagamentoByPedido(Pedido pedido);
    Optional<Pagamento> findByCartaoAndBandeiraAndNsuAndCodigoAutorizacaoAndParcelas(String cartao, String bandeira, Integer nsu, String codigoAutorizacao, Integer parcelas);

}
