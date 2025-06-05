package br.com.pedido.adapters.gateway.loja;

import br.com.pedido.domain.entities.Loja;
import br.com.pedido.infrastructure.database.LojaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LojaGatewayImpl implements LojaGateway {

    private final LojaRepository lojaRepository;

    public LojaGatewayImpl(LojaRepository lojaRepository) {
        this.lojaRepository = lojaRepository;
    }

    @Override
    public Loja save(Loja loja) {
        return lojaRepository.save(loja);
    }

    @Override
    public Optional<Loja> findById(Long id) {
        return lojaRepository.findById(id);
    }

    @Override
    public Optional<Loja> findByCnpj(String cnpj) {
        return lojaRepository.findByCnpj(cnpj);
    }

    @Override
    public Loja findByCodigo(String codigo) {
        return lojaRepository.findByCodigo(codigo);
    }

}
