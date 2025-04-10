package br.com.pedido.infrastructure.database;

import br.com.pedido.domain.entities.Loja;
import br.com.pedido.domain.entities.Pedido;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface PedidoRepository extends Repository<Pedido, Long> {

    Pedido save(Pedido pedido);
    Pedido findById(Long id);
    Optional<Pedido> findByCodigo(String codigo);
    List<Pedido> findAllByLoja(Loja loja);

}
