package br.com.transacao.application.usecases;

import br.com.commons.dto.transacao.TransacaoDto;

public interface CriarTransacao {

    void executar(TransacaoDto transacaoDto);

}
