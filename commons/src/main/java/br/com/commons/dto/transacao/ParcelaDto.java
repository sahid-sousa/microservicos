package br.com.commons.dto.transacao;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public record ParcelaDto(
        Date data,
        Integer numero,
        BigDecimal valorBruto,
        BigDecimal valorLiquido,
        BigDecimal valorDesconto
) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
}
