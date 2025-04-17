package br.com.gateway.venda.adapters.gateway;

import br.com.gateway.venda.domain.entities.Venda;
import br.com.gateway.venda.infrastructure.database.VendaRepository;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
public class VendaGatewayImpl implements VendaGateway {

    private final VendaRepository vendaRepository;

    public VendaGatewayImpl(VendaRepository vendaRepository) {
        this.vendaRepository = vendaRepository;
    }

    @Override
    public Venda save(Venda venda) {
        return vendaRepository.save(venda);
    }

    @Override
    public Optional<Venda> findByAtributos(Date dataVenda, String cartao, String codigoAutorizacao, Integer nsu, String bandeira, Integer parcelas, String tipoTransacao) {
        return vendaRepository.findByAtributos(dataVenda, cartao, codigoAutorizacao, nsu, bandeira, parcelas, tipoTransacao);
    }
}
