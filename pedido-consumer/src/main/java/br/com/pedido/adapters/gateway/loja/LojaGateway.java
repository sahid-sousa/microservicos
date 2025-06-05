package br.com.pedido.adapters.gateway.loja;

import br.com.pedido.domain.entities.Loja;

import java.util.Optional;

public interface LojaGateway {

    Loja save(Loja loja);
    Optional<Loja> findById(Long id);
    Optional<Loja> findByCnpj(String cnpj);
    Loja findByCodigo(String codigo);

}
