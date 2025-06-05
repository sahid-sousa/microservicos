package br.com.gateway.venda.application.usecase;

import br.com.commons.dto.venda.VendaDetailDto;
import br.com.gateway.venda.domain.entities.Venda;

public interface CriarVenda {

    Venda executar(VendaDetailDto vendaDetailDto);
    Venda criarVenda(VendaDetailDto vendaDetailDto);

}
