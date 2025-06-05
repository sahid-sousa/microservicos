package br.com.gateway.venda.application.usecase;

import br.com.commons.dto.pedido.PedidoDto;
import br.com.commons.dto.venda.VendaDetailDto;

import java.util.List;

public interface BuscarVenda {
    List<VendaDetailDto> executar(PedidoDto pedidoDto);
}
