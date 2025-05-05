package br.com.gateway.venda.application.services;

import br.com.commons.dto.pedido.PedidoDto;
import br.com.commons.dto.venda.VendaDetailDto;
import br.com.gateway.venda.adapters.gateway.VendaGateway;
import br.com.gateway.venda.application.usecase.BuscarVenda;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class BuscarVendaImpl implements BuscarVenda {

    private final VendaGateway vendaGateway;

    public BuscarVendaImpl(VendaGateway vendaGateway) {
        this.vendaGateway = vendaGateway;
    }

    @Override
    public List<VendaDetailDto> executar(PedidoDto pedidoDto) {
        List<VendaDetailDto> vendas = new ArrayList<>();
        for(var pagamento : pedidoDto.pagamentos()){
            vendaGateway.findByAtributos(
                    pedidoDto.data(),
                    pagamento.cartao(),
                    pagamento.codigoAutorizacao(),
                    pagamento.nsu(),
                    pagamento.bandeira(),
                    pagamento.parcelas(),
                    pagamento.tipoTransacao()
            ).ifPresent(venda -> vendas.add(new VendaDetailDto(
                    venda.getUuid(),
                    venda.getDataVenda(),
                    venda.getCartao(),
                    venda.getCodigoAutorizacao(),
                    venda.getNsu(),
                    venda.getBandeira(),
                    venda.getParcelas(),
                    venda.getTipoTransacao(),
                    venda.getValorTransacao(),
                    venda.getTaxaTransacao()
            )));
        }
        return vendas;
    }

}
