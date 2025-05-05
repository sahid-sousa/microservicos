package br.com.transacao.adpaters.gateway.transacao;

import br.com.transacao.domain.entities.StatusTransacao;
import br.com.transacao.domain.entities.Transacao;
import br.com.transacao.infrastructure.database.TransacaoRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

@Component
public class TransacaoGatewayImpl implements TransacaoGateway {

    private final TransacaoRepository transacaoRepository;

    public TransacaoGatewayImpl(TransacaoRepository transacaoRepository) {
        this.transacaoRepository = transacaoRepository;
    }

    @Override
    public Transacao save(Transacao transacao) {
        return transacaoRepository.save(transacao);
    }

    @Override
    public Optional<Transacao> findByAtributos(
            Date data,
            Integer quantidadeParcelas,
            BigDecimal valor,
            String tipoTransacao,
            String cartao,
            String codigoAutorizacao,
            Integer nsu,
            String bandeira,
            StatusTransacao status
    ) {
        return transacaoRepository.findByAtributos(
                data,
                quantidadeParcelas,
                valor,
                tipoTransacao,
                cartao,
                codigoAutorizacao,
                nsu,
                bandeira,
                status
        );
    }
}
