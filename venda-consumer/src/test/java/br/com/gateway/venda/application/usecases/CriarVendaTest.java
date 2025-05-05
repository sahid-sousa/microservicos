package br.com.gateway.venda.application.usecases;

import br.com.commons.dto.venda.VendaDetailDto;
import br.com.gateway.venda.application.usecase.CriarVenda;
import br.com.gateway.venda.domain.entities.Venda;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Date;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class CriarVendaTest {

    @Autowired
    CriarVenda criarVenda;

    @Test
    public void executarVenda() {
        VendaDetailDto vendaDetail = new VendaDetailDto(
                "uuid-venda-001",
                new Date(),
                "123456******7890",
                "AUTZ987654",
                123456,
                "MASTERCARD",
                5,
                "DEBITO",
                new BigDecimal("350.75"),
                new BigDecimal("7.25")
        );

        Venda venda = criarVenda.executar(vendaDetail);

        Assertions.assertNotNull(venda.getId());
        Assertions.assertEquals(vendaDetail.codigoAutorizacao(), venda.getCodigoAutorizacao());

    }

    @Test
    public void deveCriarVenda() {
        VendaDetailDto novaVendaDetail = new VendaDetailDto(
                "uuid-venda-001",
                new Date(),
                "123456******7890",
                "AUTZ987654",
                123456,
                "MASTERCARD",
                5,
                "DEBITO",
                new BigDecimal("350.75"),
                new BigDecimal("7.25")
        );

        Venda venda = criarVenda.criarVenda(novaVendaDetail);

        Assertions.assertNull(venda.getId());
        Assertions.assertEquals(novaVendaDetail.codigoAutorizacao(), venda.getCodigoAutorizacao());

    }
}
