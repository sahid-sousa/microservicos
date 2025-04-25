package br.com.gateway.venda.application.usecase;

import br.com.commons.dto.venda.VendaDetailDto;
import br.com.gateway.venda.adapters.gateway.VendaGateway;
import br.com.gateway.venda.domain.entities.Venda;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CriarVenda {

    private final VendaGateway vendaGateway;

    public CriarVenda(VendaGateway vendaGateway) {
        this.vendaGateway = vendaGateway;
    }

    public void executar(VendaDetailDto vendaDetailDto) {
        Venda venda = criarVenda(vendaDetailDto);
        vendaGateway.save(venda);
    }

    private Venda criarVenda(VendaDetailDto vendaDetailDto) {
        return vendaGateway.findByAtributos(
                vendaDetailDto.dataVenda(),
                vendaDetailDto.cartao(),
                vendaDetailDto.codigoAutorizacao(),
                vendaDetailDto.nsu(),
                vendaDetailDto.bandeira(),
                vendaDetailDto.parcelas(),
                vendaDetailDto.tipoTransacao()
        ).orElseGet(() -> {
            Venda novo = new Venda();
            novo.setDataVenda(vendaDetailDto.dataVenda());
            novo.setCartao(vendaDetailDto.cartao());
            novo.setCodigoAutorizacao(vendaDetailDto.codigoAutorizacao());
            novo.setNsu(vendaDetailDto.nsu());
            novo.setBandeira(vendaDetailDto.bandeira());
            novo.setParcelas(vendaDetailDto.parcelas());
            novo.setTipoTransacao(vendaDetailDto.tipoTransacao());
            novo.setValorTransacao(vendaDetailDto.valorTransacao());
            novo.setTaxaTransacao(vendaDetailDto.taxaTransacao());
            return novo;
        });
    }

}
