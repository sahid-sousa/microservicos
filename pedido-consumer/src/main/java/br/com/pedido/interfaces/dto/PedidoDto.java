package br.com.pedido.interfaces.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public record PedidoDto(
        String codigo,
        Double valor,
        Date data,
        Boolean faturado,
        LojaDto loja,
        List<PagamentoDto> pagamentos
) implements Serializable {
    private static final long serialVersionUID = 1L;
}
