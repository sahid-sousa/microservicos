package br.com.transacao.infrastructure.databases;

import br.com.transacao.domain.entities.StatusTransacao;
import br.com.transacao.domain.entities.Transacao;
import br.com.transacao.infrastructure.database.TransacaoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class TransacaoRepositoryTest {

    @Autowired
    TransacaoRepository transacaoRepository;

    @Test
    public void deveSalvarTrasacao() throws ParseException {

        Transacao novo = new Transacao();

        String dataTexto = "05/05/2025";
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date data = formatter.parse(dataTexto);

        novo.setCodigoLoja("LOJA123");
        novo.setCodigoPedido("PEDIDO456");
        novo.setUuidVenda("uuid-abc-123");
        novo.setData(data);
        novo.setValor(new BigDecimal("150.00"));
        novo.setTaxa(new BigDecimal("2.50"));
        novo.setTipoTransacao("CREDITO");
        novo.setCartao("123456******7890");
        novo.setCodigoAutorizacao("AUTH123456");
        novo.setNsu(987654);
        novo.setBandeira("VISA");

        Transacao transacao = transacaoRepository.save(novo);


        Assertions.assertNotNull(transacao.getId());
        Assertions.assertEquals(novo.getCodigoPedido(), transacao.getCodigoPedido());

    }

    @Test
    public void deveBuscarTrasacao() throws ParseException {
        Transacao novo = new Transacao();

        String dataTexto = "05/05/2025";
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date data = formatter.parse(dataTexto);


        novo.setCodigoLoja("LOJA124");
        novo.setCodigoPedido("PEDIDO4567");
        novo.setUuidVenda("uuid-abc-123");
        novo.setData(data);
        novo.setQuantidadeParcelas(1);
        novo.setValor(new BigDecimal("150.00"));
        novo.setTaxa(new BigDecimal("2.50"));
        novo.setTipoTransacao("CREDITO");
        novo.setCartao("123456******7890");
        novo.setCodigoAutorizacao("AUTH123456");
        novo.setNsu(987654);
        novo.setBandeira("VISA");

        Transacao transacao = transacaoRepository.save(novo);

        Optional<Transacao> transacaoEncontrada = transacaoRepository.findByAtributos(
                transacao.getData(),
                transacao.getQuantidadeParcelas(),
                transacao.getValor(),
                transacao.getTipoTransacao(),
                transacao.getCartao(),
                transacao.getCodigoAutorizacao(),
                transacao.getNsu(),
                transacao.getBandeira(),
                StatusTransacao.PENDENTE
        );

        Assertions.assertTrue(transacaoEncontrada.isPresent());
        Assertions.assertEquals(novo.getCodigoPedido(), transacaoEncontrada.get().getCodigoPedido());

    }



}
