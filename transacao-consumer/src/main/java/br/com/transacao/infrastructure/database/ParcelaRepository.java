package br.com.transacao.infrastructure.database;

import br.com.transacao.domain.entities.Parcela;
import br.com.transacao.domain.entities.Transacao;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;


public interface ParcelaRepository extends Repository<Parcela, Long> {

    Parcela save(Parcela parcela);
    Optional<List<Parcela>> findAllByTransacao(Transacao transacao);

}
