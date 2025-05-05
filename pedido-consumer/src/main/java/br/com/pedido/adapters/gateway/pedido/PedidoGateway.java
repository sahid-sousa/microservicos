package br.com.pedido.adapters.gateway.pedido;

import br.com.pedido.domain.entities.Loja;
import br.com.pedido.domain.entities.Pedido;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PedidoGateway {

    Pedido save(Pedido pedido);
    Optional<Pedido> findByCodigo(String codigo);
    Optional<Pedido> findByUuid(String uuid);
    Pedido findById(Long id);
    List<Pedido> findAllByLoja(Loja loja);
    List<Long> findAllStatus(Boolean faturado, Boolean conciliado, Pageable pageable);

}
