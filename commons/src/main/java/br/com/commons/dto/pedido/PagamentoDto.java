package br.com.commons.dto.pedido;

import java.io.Serial;
import java.io.Serializable;

public record PagamentoDto (
        String uuid,
        String tipo,
        String tipoTransacao,
        Integer parcelas,
        String cartao,
        String codigoAutorizacao,
        Integer nsu,
        String bandeira,
        Double valor
) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
}
