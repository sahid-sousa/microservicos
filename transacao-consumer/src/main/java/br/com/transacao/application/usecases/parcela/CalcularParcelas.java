package br.com.transacao.application.usecases.parcela;

import br.com.commons.dto.transacao.ParcelaDto;
import br.com.transacao.domain.entities.Transacao;

import java.util.List;

public interface CalcularParcelas {
    List<ParcelaDto> calcular(Transacao transacao);
}
