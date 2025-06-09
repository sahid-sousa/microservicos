package br.com.pedido.application.services;

import br.com.commons.dto.pedido.PagamentoDto;
import br.com.commons.dto.pedido.PedidoDto;
import br.com.pedido.adapters.gateway.loja.LojaGateway;
import br.com.pedido.adapters.gateway.pagamento.PagamentoGateway;
import br.com.pedido.adapters.gateway.pedido.PedidoGateway;
import br.com.pedido.application.usecases.CriarPedido;
import br.com.pedido.domain.entities.Loja;
import br.com.pedido.domain.entities.Pagamento;
import br.com.pedido.domain.entities.Pedido;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class CriarPedidoImpl implements CriarPedido {

    private final PedidoGateway pedidoGateway;
    private final PagamentoGateway pagamentoGateway;
    private final LojaGateway lojaGateway;

    public CriarPedidoImpl(PedidoGateway pedidoGateway, PagamentoGateway pagamentoGateway, LojaGateway lojaGateway) {
        this.pedidoGateway = pedidoGateway;
        this.pagamentoGateway = pagamentoGateway;
        this.lojaGateway = lojaGateway;
    }

    @Override
    public void executar(PedidoDto pedidoDto) {
        log.info("Criando pedido: {}", pedidoDto.codigo());
        Pedido pedido = criarPedido(pedidoDto);
        if (pedido.getId() == null) {
            criarLoja(pedidoDto, pedido);
            criarPagamento(pedidoDto, pedido);
            log.info("Pedido criado: {}", pedido.getCodigo());
        } else {
            log.info("Pedido atualizado: {}", pedido.getFaturado());
            pedidoGateway.save(pedido);
        }

    }

    public Pedido criarPedido(PedidoDto pedidoDto) {
        return pedidoGateway.findByCodigo(pedidoDto.codigo())
                .map( existente -> {
                    existente.setFaturado(pedidoDto.faturado());
                    return existente;
                }).orElseGet(() -> {
                    Pedido novo = new Pedido();
                    novo.setCodigo(pedidoDto.codigo());
                    novo.setValor(pedidoDto.valor());
                    novo.setData(pedidoDto.data());
                    novo.setFaturado(pedidoDto.faturado());
                    return novo;
                });
    }

    public void criarLoja(PedidoDto pedidoDto, Pedido pedido) {
        Loja loja = lojaGateway.findByCnpj(pedidoDto.loja().cnpj()).orElseGet(() -> {
            Loja nova = new Loja();
            nova.setCodigo(pedidoDto.loja().codigo());
            nova.setCnpj(pedidoDto.loja().cnpj());
            return nova;
        });
        loja.adicionarPedido(pedido);
        lojaGateway.save(loja);
    }

    public void criarPagamento(PedidoDto pedidoDto, Pedido pedido) {
        pedidoDto.pagamentos().forEach( pagamentoDto -> {
            Optional<Pagamento> pagamento = pagamentoGateway.findByCartaoAndBandeiraAndNsuAndCodigoAutorizacaoAndParcelas(
                    pagamentoDto.cartao(),
                    pagamentoDto.bandeira(),
                    pagamentoDto.nsu(),
                    pagamentoDto.codigoAutorizacao(),
                    pagamentoDto.parcelas()
            );
            if(pagamento.isEmpty()) {
                Pagamento novo = getNovoPagamento(pedido, pagamentoDto);
                pedido.adicionarPagamento(novo);
                pagamentoGateway.save(novo);
            }
        });
    }

    public Pagamento getNovoPagamento(Pedido pedido, PagamentoDto pagamentoDto) {
        Pagamento novo = new Pagamento();
        novo.setTipo(pagamentoDto.tipo());
        novo.setTipoTransacao(pagamentoDto.tipoTransacao());
        novo.setParcelas(pagamentoDto.parcelas());
        novo.setCartao(pagamentoDto.cartao());
        novo.setCodigoAutorizacao(pagamentoDto.codigoAutorizacao());
        novo.setNsu(pagamentoDto.nsu());
        novo.setBandeira(pagamentoDto.bandeira());
        novo.setValor(pagamentoDto.valor());
        novo.setPedido(pedido);
        return novo;
    }

}
