package br.com.transacao.application.usecases.transacao;

import br.com.commons.dto.transacao.TransacaoDto;
import br.com.transacao.domain.entities.StatusTransacao;
import br.com.transacao.domain.entities.Transacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class CriarTransacaoTest {

    @Mock
    CriarTransacao criarTransacao;

    private SimpleDateFormat formatter;

    @BeforeEach
    public void setup() {
        formatter = new SimpleDateFormat("dd/MM/yyyy");
    }

    @Test
    @DisplayName("Test Criar Transacao")
    public void testCriarTransacao() throws ParseException {
        //Given
        Transacao transacao = new Transacao();
        transacao.setCodigoLoja("LOJA123");
        transacao.setCodigoPedido("PEDIDO456");
        transacao.setValor(new BigDecimal("100.00"));
        transacao.setTaxa(new BigDecimal("1"));
        transacao. setStatus(StatusTransacao.PENDENTE);
        transacao.setCartao("123456******7890");
        transacao.setCodigoAutorizacao("AUTH123456");
        transacao.setNsu(987654);
        transacao.setBandeira("VISA");
        transacao.setParcelas(new ArrayList<>());
        transacao.setData(formatter.parse("07/05/2025"));
        transacao. setTipoTransacao("DEBITO");
        transacao.setQuantidadeParcelas(1);

        TransacaoDto dto = new TransacaoDto(
                "LOJA123",
                "PEDIDO456",
                "uuid-abc-123",
                formatter.parse("07/05/2025"),
                1,
                new BigDecimal("100.00"),
                new BigDecimal("1"),
                "DEBITO",
                "123456******7890",
                "AUTH123456",
                987654,
                "VISA"
        );

        given(criarTransacao.criarTransacao(any())).willReturn(transacao);

        Transacao entity = criarTransacao.criarTransacao(dto);

        assertNotNull(entity);
        assertEquals("LOJA123", entity.getCodigoLoja());
        assertEquals("PEDIDO456", entity.getCodigoPedido());
        assertEquals(new BigDecimal("100.00"), entity.getValor());
        assertEquals(StatusTransacao.PENDENTE, entity.getStatus());
        assertEquals("123456******7890", entity.getCartao());
        assertEquals("DEBITO", entity.getTipoTransacao());
        assertEquals("VISA", entity.getBandeira());
    }

}
