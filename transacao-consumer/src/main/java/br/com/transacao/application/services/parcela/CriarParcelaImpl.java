package br.com.transacao.application.services.parcela;

import br.com.commons.dto.GenericBuilder;
import br.com.commons.dto.transacao.ParcelaDto;
import br.com.transacao.application.usecases.parcela.CriarParcela;
import br.com.transacao.domain.entities.Parcela;
import br.com.transacao.domain.entities.StatusTransacao;
import br.com.transacao.domain.entities.Transacao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CriarParcelaImpl implements CriarParcela {

    @Override
    public Parcela criar(Transacao transacao, ParcelaDto parcelaDto) {
        Parcela parcela = GenericBuilder.of(Parcela.class)
                .with(p -> p.setData(parcelaDto.data()))
                .with(p -> p.setNumero(parcelaDto.numero()))
                .with(p -> p.setValorBruto(parcelaDto.valorBruto()))
                .with(p -> p.setValorLiquido(parcelaDto.valorLiquido()))
                .with(p -> p.setValorDesconto(parcelaDto.valorDesconto()))
                .with(p -> p.setTransacao(transacao))
                .build();
        transacao.setStatus(StatusTransacao.AGENDADO);
        transacao.adicionarParcela(parcela);
        return parcela;
    }

}
