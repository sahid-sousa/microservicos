package br.com.transacao.application.services;

import br.com.commons.dto.GenericBuilder;
import br.com.commons.dto.transacao.TransacaoDto;
import br.com.transacao.adpaters.gateway.transacao.TransacaoGateway;
import br.com.transacao.application.usecases.CriarTransacao;
import br.com.transacao.domain.entities.StatusTransacao;
import br.com.transacao.domain.entities.Transacao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CriarTransacaoImpl implements CriarTransacao {

    private final TransacaoGateway transacaoGateway;

    public CriarTransacaoImpl(TransacaoGateway transacaoGateway) {
        this.transacaoGateway = transacaoGateway;
    }

    @Override
    public void executar(TransacaoDto transacaoDto) {
        Transacao transacao = criarTransacao(transacaoDto);
        if (transacao.getId() == null) {
            log.info("Criando Transacao: {} Codigo Aut.: {}", transacao.getTipoTransacao(), transacao.getCodigoAutorizacao());
            transacaoGateway.save(transacao);
        } else {
            log.info("Transacao: {}", transacao.getUuid());
        }
    }

    private Transacao criarTransacao(TransacaoDto transacaoDto) {
        return transacaoGateway.findByAtributos(
                transacaoDto.data(),
                transacaoDto.totalParcelas(),
                transacaoDto.valor(),
                transacaoDto.tipoTransacao(),
                transacaoDto.cartao(),
                transacaoDto.codigoAutorizacao(),
                transacaoDto.nsu(),
                transacaoDto.bandeira(),
                StatusTransacao.PENDENTE
        ).orElseGet(GenericBuilder.of(Transacao.class)
                .with(t -> t.setCodigoLoja(transacaoDto.codigoLoja()))
                .with(t -> t.setCodigoPedido(transacaoDto.codigoPedido()))
                .with(t -> t.setUuidVenda(transacaoDto.uuidVenda()))
                .with(t -> t.setData(transacaoDto.data()))
                .with(t -> t.setQuantidadeParcelas(transacaoDto.totalParcelas()))
                .with(t -> t.setValor(transacaoDto.valor()))
                .with(t -> t.setTaxa(transacaoDto.taxa()))
                .with(t -> t.setTipoTransacao(transacaoDto.tipoTransacao()))
                .with(t -> t.setCartao(transacaoDto.cartao()))
                .with(t -> t.setCodigoAutorizacao(transacaoDto.codigoAutorizacao()))
                .with(t -> t.setNsu(transacaoDto.nsu()))
                .with(t -> t.setBandeira(transacaoDto.bandeira()))::build
        );
    }

}
