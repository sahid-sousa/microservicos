package br.com.transacao.infrastructure.database;

import static org.junit.jupiter.api.Assertions.*;

import br.com.transacao.domain.entities.Parcela;
import br.com.transacao.domain.entities.StatusTransacao;
import br.com.transacao.domain.entities.Transacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@DataJpaTest
class ParcelaRepositoryTest {

    @Autowired
    TransacaoRepository transacaoRepository;

    @Autowired
    ParcelaRepository parcelaRepository;

    private Transacao transacao;
    private Parcela parcela;

    @BeforeEach
    public void setup() throws ParseException {
        //Given
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        transacao = new Transacao();
        transacao.setCodigoLoja("L00001");
        transacao.setCodigoPedido("P00001");
        transacao.setData(formatter.parse("07/05/2025"));
        transacao.setQuantidadeParcelas(1);
        transacao.setValor(new BigDecimal(150));
        transacao.setTaxa(new BigDecimal(2));
        transacao.setTipoTransacao("DEBITO");
        transacao.setStatus(StatusTransacao.PENDENTE);
        transacao.setCartao("123456*******789");
        transacao.setCodigoAutorizacao("156352");
        transacao.setNsu(1258);
        transacao.setBandeira("VISA");

        parcela = new Parcela();
        parcela.setData(formatter.parse("18/06/2025"));
        parcela.setNumero(1);
        parcela.setValorBruto(new BigDecimal("100.00"));
        parcela.setValorLiquido(new BigDecimal("99.00"));
        parcela.setValorDesconto(new BigDecimal("1"));

        parcela.setTransacao(transacao);

    }

    @Test
    @DisplayName("Test given Parcela Object  when save Parcela then return saved Parcela")
    void testGivenParcelaObject_whenSave_thenReturnSavedParcela() {
        //When
        transacaoRepository.save(transacao);
        Parcela entity = parcelaRepository.save(parcela);

        //Then
        assertNotNull(entity);
    }

    @Test
    @DisplayName("Test given Parcela List Optional Object  when findAllByTransacao then return List Optional Parcela")
    void testGivenParcelaListOptionalObject_whenfindAllByTransacao_thenReturnListOptionalParcela() {
        //When
        transacaoRepository.save(transacao);
        parcelaRepository.save(parcela);

        Optional<List<Parcela>> optionalParcelaList = parcelaRepository.findAllByTransacao(transacao);
        assertTrue(optionalParcelaList.isPresent());

        List<Parcela> parcelaInOptionalList = optionalParcelaList.get();

        //Then
        assertFalse(parcelaInOptionalList.isEmpty());
        assertEquals(1, parcelaInOptionalList.get(0).getNumero());
        assertEquals(new BigDecimal("100.00"), parcelaInOptionalList.get(0).getValorBruto());
        assertEquals(new BigDecimal("99.00"), parcelaInOptionalList.get(0).getValorLiquido());
        assertEquals(new BigDecimal("1"), parcelaInOptionalList.get(0).getValorDesconto());

    }



}