package br.com.pedido.infrastructure.database;

import br.com.pedido.domain.entities.Loja;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface LojaRepository extends Repository<Loja, Long> {

    Loja save(Loja loja);
    Loja findById(Long id);
    Optional<Loja> findByCnpj(String cnpj);
    Loja findByCodigo(String codigo);

}
