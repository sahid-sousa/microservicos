package br.com.pedido.adapters.gateway.pedido;

import br.com.pedido.domain.entities.Loja;
import br.com.pedido.domain.entities.Pedido;
import br.com.pedido.infrastructure.database.PedidoRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class PedidoGatewayImpl implements PedidoGateway {

    private final PedidoRepository pedidoRepository;

    public PedidoGatewayImpl(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @Override
    public Pedido save(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    @Override
    public Pedido findById(Long id) {
        return pedidoRepository.findById(id);
    }

    @Override
    public Optional<Pedido> findByCodigo(String codigo) {
        return pedidoRepository.findByCodigo(codigo);
    }

    @Override
    public Optional<Pedido> findByUuid(String uuid) {
        return pedidoRepository.findByUuid(uuid);
    }

    @Override
    public List<Pedido> findAllByLoja(Loja loja) {
        return pedidoRepository.findAllByLoja(loja);
    }

    @Override
    public List<Long> findAllStatus(Boolean faturado, Boolean conciliado, Pageable pageable) {
        return pedidoRepository.findAllStatus(faturado, conciliado, pageable);
    }

}
