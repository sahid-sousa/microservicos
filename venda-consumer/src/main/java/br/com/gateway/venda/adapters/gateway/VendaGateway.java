package br.com.gateway.venda.adapters.gateway;

import br.com.gateway.venda.domain.entities.Venda;

import java.util.Date;
import java.util.Optional;

public interface VendaGateway {
    Venda save(Venda venda);
    Optional<Venda> findByAtributos(
            Date dataVenda,
            String cartao,
            String codigoAutorizacao,
            Integer nsu,
            String bandeira,
            Integer parcelas,
            String tipoTransacao
    );
}
