package br.com.transacao.application.services.transacao;


import br.com.commons.dto.GenericBuilder;
import br.com.commons.dto.transacao.ParcelaDto;
import br.com.commons.dto.transacao.TransacaoDto;
import br.com.transacao.adpaters.gateway.parcela.ParcelaGateway;
import br.com.transacao.adpaters.gateway.transacao.TransacaoGateway;
import br.com.transacao.application.usecases.parcela.CalcularParcelas;
import br.com.transacao.application.usecases.parcela.CriarParcela;
import br.com.transacao.application.usecases.transacao.CriarTransacao;
import br.com.transacao.domain.entities.Parcela;
import br.com.transacao.domain.entities.StatusTransacao;
import br.com.transacao.domain.entities.Transacao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CriarTransacaoImpl implements CriarTransacao {

    private final TransacaoGateway transacaoGateway;
    private final ParcelaGateway parcelaGateway;
    private final CalcularParcelas calcularParcelas;
    private final CriarParcela criarParcela;

    public CriarTransacaoImpl(TransacaoGateway transacaoGateway, ParcelaGateway parcelaGateway, CalcularParcelas calcularParcelas, CriarParcela criarParcela) {
        this.transacaoGateway = transacaoGateway;
        this.parcelaGateway = parcelaGateway;
        this.calcularParcelas = calcularParcelas;
        this.criarParcela = criarParcela;
    }

    @Override
    public void executar(TransacaoDto transacaoDto) {
        Transacao transacao = criarTransacao(transacaoDto);
        if (transacao.getParcelas().isEmpty()) {
            transacaoGateway.save(transacao);
            List<ParcelaDto> parcelas = calcularParcelas.calcular(transacao);
            log.info("Agendamento:");
            for (ParcelaDto parcelaDto : parcelas) {
                Parcela parcela = criarParcela.criar(transacao, parcelaDto);
                parcelaGateway.save(parcela);
                log.info(" - Transacao: #{} {} Codigo Aut.: {} Dt.: {} St.: {}", transacao.getId(), transacao.getTipoTransacao(), transacao.getCodigoAutorizacao(), transacao.getData(), transacao.getStatus());
                log.info(" - Parcela Num: {} Vlb: {} Dt.: {}", parcela.getNumero(), parcela.getValorBruto(), parcela.getData());
            }
        } else {
            log.info(" - Transacao: #{} {} Codigo Aut.: {} Dt.: {} St.: {}", transacao.getId(), transacao.getTipoTransacao(), transacao.getCodigoAutorizacao(), transacao.getData(), transacao.getStatus());
        }
    }

    @Override
    public Transacao criarTransacao(TransacaoDto transacaoDto) {
        return transacaoGateway.findByAtributos(
                transacaoDto.data(),
                transacaoDto.totalParcelas(),
                transacaoDto.valor(),
                transacaoDto.tipoTransacao(),
                transacaoDto.cartao(),
                transacaoDto.codigoAutorizacao(),
                transacaoDto.nsu(),
                transacaoDto.bandeira(),
                StatusTransacao.AGENDADO
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
