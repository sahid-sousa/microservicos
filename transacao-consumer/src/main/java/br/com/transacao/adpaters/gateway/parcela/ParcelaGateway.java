package br.com.transacao.adpaters.gateway.parcela;

import br.com.transacao.domain.entities.Parcela;
import br.com.transacao.domain.entities.Transacao;
import java.util.List;
import java.util.Optional;

public interface ParcelaGateway {

    Parcela save(Parcela parcela);
    Optional<List<Parcela>> findAllByTransacao(Transacao transacao);

}
