package br.com.transacao.application.usecases.transacao;

import br.com.commons.dto.transacao.TransacaoDto;
import br.com.transacao.domain.entities.Transacao;

public interface CriarTransacao {

    void executar(TransacaoDto transacaoDto);
    Transacao criarTransacao(TransacaoDto transacaoDto);

}
