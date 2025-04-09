package br.com.pedido.interfaces.dto;

import java.io.Serializable;

public record PagamentoDto (
        String tipo,
        String tipoTransacao,
        Integer parcelas,
        String cartao,
        String codigoAutorizacao,
        Integer nsu,
        String bandeira,
        Double valor
) implements Serializable {
    private static final long serialVersionUID = 1L;
}

