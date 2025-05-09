package br.com.transacao.application.services.parcela;

import br.com.commons.dto.transacao.ParcelaDto;
import br.com.transacao.application.usecases.calendario.DiaUtil;
import br.com.transacao.application.usecases.parcela.CalcularParcelas;
import br.com.transacao.domain.entities.Transacao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Slf4j
@Service
public class CalcularParcelasImpl implements CalcularParcelas {

    DiaUtil diaUtil;

    public CalcularParcelasImpl(DiaUtil diaUtil) {
        this.diaUtil = diaUtil;
    }

    @Override
    public List<ParcelaDto> calcular(Transacao transacao) {
        List<ParcelaDto> parcelas = new ArrayList<>();
        BigDecimal valorTransacao = transacao.getValor();
        BigDecimal valorTaxa = transacao.getTaxa();

        Integer quantidadeParcelas = transacao.getQuantidadeParcelas();

        if (quantidadeParcelas == 1) {
            parcelas.add(
                    new ParcelaDto(
                            dataPagamentoParcela(transacao,quantidadeParcelas),
                            quantidadeParcelas,
                            transacao.getValor(),
                            (transacao.getValor().subtract(valorTaxa)),
                            transacao.getTaxa()
                    )
            );
        } else {

            BigDecimal valorParcela = valorTransacao.divide(new BigDecimal(quantidadeParcelas), 2, RoundingMode.HALF_UP);
            BigDecimal valorDiferenca = valorTransacao.subtract(valorParcela.multiply(new BigDecimal(quantidadeParcelas)));

            BigDecimal valorTaxaParcela = valorTaxa.divide(new BigDecimal(quantidadeParcelas), 2, RoundingMode.HALF_UP);
            BigDecimal valorTaxaParcelaDiferenca = valorTaxa.subtract(valorTaxaParcela.multiply(new BigDecimal(quantidadeParcelas)));

            for (int i = 1; i <= quantidadeParcelas; i++) {

                BigDecimal valorDesconto = (i == 1)  ? (valorTaxaParcela.add(valorTaxaParcelaDiferenca)) : valorTaxaParcela;
                BigDecimal valorBruto = (i == 1) ? (valorParcela.add(valorDiferenca)) : valorParcela;
                BigDecimal valorLiquido = valorBruto.subtract(valorDesconto);

                parcelas.add(
                        new ParcelaDto(
                                dataPagamentoParcela(transacao, i),
                                i,
                                valorBruto,
                                valorLiquido,
                                valorDesconto
                        )
                );
            }

        }

        return parcelas;
    }

    private Date dataPagamentoParcela(Transacao transacao, Integer parcela) {
        Date date;
        if (transacao.getTipoTransacao().equals("DEBITO")) {
            date = diaUtil.diasUteis(transacao.getData(), (parcela));
        } else {
            date = diaUtil.diasUteis(transacao.getData(), (30 * parcela));
        }
        return date;
    }

}
