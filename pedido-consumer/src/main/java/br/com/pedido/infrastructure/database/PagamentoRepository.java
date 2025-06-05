package br.com.pedido.infrastructure.database;

import br.com.pedido.domain.entities.Pagamento;
import br.com.pedido.domain.entities.Pedido;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface PagamentoRepository extends Repository<Pagamento, Long> {

    Pagamento save(Pagamento pagamento);
    Pagamento findById(Long id);
    List<Pagamento> findPagamentoByPedido(Pedido pedido);
    Optional<Pagamento> findByCartaoAndBandeiraAndNsuAndCodigoAutorizacaoAndParcelas(String cartao, String bandeira, Integer nsu, String codigoAutorizacao, Integer parcelas);

}
