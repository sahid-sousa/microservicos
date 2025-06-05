package br.com.commons.dto.venda;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public record VendaDetailDto(
        String uuid,
        Date dataVenda,
        String cartao,
        String codigoAutorizacao,
        Integer nsu,
        String bandeira,
        Integer parcelas,
        String tipoTransacao,
        BigDecimal valorTransacao,
        BigDecimal taxaTransacao
) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
}
