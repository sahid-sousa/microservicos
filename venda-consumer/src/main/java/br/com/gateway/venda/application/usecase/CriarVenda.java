package br.com.gateway.venda.application.usecase;

import br.com.commons.dto.venda.VendaDetailDto;

public interface CriarVenda {

    void executar(VendaDetailDto vendaDetailDto);

}
