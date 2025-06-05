package br.com.pedido.application.usecases;

import br.com.commons.dto.pedido.PedidoDto;

public interface CriarPedido {

    void executar(PedidoDto pedidoDto);

}
