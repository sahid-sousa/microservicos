package br.com.commons.dto.transacao;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public record TransacaoDto(
        String codigoLoja,
        String codigoPedido,
        String uuidVenda,
        Date data,
        Integer totalParcelas,
        BigDecimal valor,
        BigDecimal taxa,
        String tipoTransacao,
        String cartao,
        String codigoAutorizacao,
        Integer nsu,
        String bandeira
) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
}
