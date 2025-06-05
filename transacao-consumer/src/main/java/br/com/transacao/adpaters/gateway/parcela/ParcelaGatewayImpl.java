package br.com.transacao.adpaters.gateway.parcela;

import br.com.transacao.domain.entities.Parcela;
import br.com.transacao.domain.entities.Transacao;
import br.com.transacao.infrastructure.database.ParcelaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ParcelaGatewayImpl implements ParcelaGateway {

    private final ParcelaRepository parcelaRepository;

    public ParcelaGatewayImpl(ParcelaRepository parcelaRepository) {
        this.parcelaRepository = parcelaRepository;
    }

    @Override
    public Parcela save(Parcela parcela) {
        return parcelaRepository.save(parcela);
    }

    @Override
    public Optional<List<Parcela>> findAllByTransacao(Transacao transacao) {
        return parcelaRepository.findAllByTransacao(transacao);
    }
}
