package br.com.transacao.application.usecases.parcela;

import br.com.commons.dto.transacao.ParcelaDto;
import br.com.transacao.domain.entities.Parcela;
import br.com.transacao.domain.entities.Transacao;

public interface CriarParcela {

    Parcela criar(Transacao transacao, ParcelaDto parcelaDto);

}
