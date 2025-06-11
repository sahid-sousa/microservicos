package br.com.transacao.application.services.parcela;

import br.com.commons.dto.transacao.ParcelaDto;
import br.com.transacao.domain.entities.Parcela;
import br.com.transacao.domain.entities.StatusTransacao;
import br.com.transacao.domain.entities.Transacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
class CriarParcelaImplTest {


    @InjectMocks
    CriarParcelaImpl criarParcela;


    private Transacao transacao;
    private ParcelaDto parcelaDto;

    @BeforeEach
    public void setup() throws ParseException {
        //Given
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        transacao = new Transacao();
        transacao.setCodigoLoja("LOJA123");
        transacao.setCodigoPedido("PEDIDO456");
        transacao.setData(formatter.parse("07/05/2025"));
        transacao.setQuantidadeParcelas(1);
        transacao.setValor(new BigDecimal("100.00"));
        transacao.setTaxa(new BigDecimal("1"));
        transacao. setTipoTransacao("CREDITO");
        transacao. setStatus(StatusTransacao.PENDENTE);
        transacao.setCartao("123456*******789");
        transacao.setCodigoAutorizacao("AUTH123456");
        transacao.setNsu(987654);
        transacao.setBandeira("VISA");
        transacao.setParcelas(new ArrayList<>());

        parcelaDto = new ParcelaDto(
                formatter.parse("18/06/2025"),
                1,
                new BigDecimal("100.00"),
                new BigDecimal("99.00"),
                new BigDecimal("1")
        );
    }

    @Test
    @DisplayName("Test execute creation Parcela of Transaction")
    public void testExecuteCreationParcelaOfTransaction() {
        //When
        Parcela entity = criarParcela.criar(transacao, parcelaDto);

        //Then
        assertNotNull(entity);
        assertNotNull(entity.getTransacao());
        assertEquals(1, parcelaDto.numero());
        assertEquals(new BigDecimal("100.00"), entity.getValorBruto());
        assertEquals(new BigDecimal("99.00"), entity.getValorLiquido());
        assertEquals(new BigDecimal(1), entity.getValorDesconto());
    }




}