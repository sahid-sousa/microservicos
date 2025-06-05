package br.com.commons.dto.pedido;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public record PedidoDto(
        String uuid,
        String codigo,
        Double valor,
        Date data,
        Boolean faturado,
        Boolean conciliado,
        LojaDto loja,
        List<PagamentoDto> pagamentos
) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
}
