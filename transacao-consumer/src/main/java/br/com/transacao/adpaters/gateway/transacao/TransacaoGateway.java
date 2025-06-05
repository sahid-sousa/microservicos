package br.com.transacao.adpaters.gateway.transacao;

import br.com.transacao.domain.entities.StatusTransacao;
import br.com.transacao.domain.entities.Transacao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

public interface TransacaoGateway {

    Transacao save(Transacao transacao);
    Optional<Transacao> findByAtributos(
            Date data,
            Integer quantidadeParcelas,
            BigDecimal valor,
            String tipoTransacao,
            String cartao,
            String codigoAutorizacao,
            Integer nsu,
            String bandeira,
            StatusTransacao status
    );

}
