package br.com.pedido.infrastructure.database;

import br.com.pedido.domain.entities.Loja;
import br.com.pedido.domain.entities.Pedido;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PedidoRepository extends Repository<Pedido, Long> {

    Pedido save(Pedido pedido);
    Pedido findById(Long id);
    Optional<Pedido> findByCodigo(String codigo);
    Optional<Pedido> findByUuid(String uuid);
    List<Pedido> findAllByLoja(Loja loja);

    @Query("SELECT p.id FROM Pedido p where p.faturado =:faturado AND p.conciliado =:conciliado")
    List<Long> findAllStatus(
            @Param("faturado") Boolean faturado,
            @Param("conciliado") Boolean conciliado,
            Pageable pageable
    );

}
