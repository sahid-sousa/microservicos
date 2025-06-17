package br.com.gateway.venda.infrastructure.route;

import br.com.commons.dto.venda.VendaDetailDto;
import br.com.gateway.venda.infrastructure.broker.Producer;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.DefaultExchange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FileReaderTest {

    @Spy
    @InjectMocks
    FileReader fileReader;

    @Mock
    Producer<VendaDetailDto> producer;

    @Mock
    private Exchange exchange;

    @Mock
    private Message message;

    private String arquivoPagamentoGateway;
    private String[] fields;


    @BeforeEach
    public void setup() {
        //Given
        arquivoPagamentoGateway = """
                001;07082023;06082023
                002;06082023;123456*1234;AAA001;7965;VISA;3;CREDITO;100,00;2,00
                002;06082023;123456*1234;BBB002;8522;ELO;1;DEBITO;50,00;2,00
                002;06082023;123456*1234;BBB003;8523;ELO;1;DEBITO;30,00;2,00
                002;06082023;123456*1234;AAA002;7966;MASTERCARD;1;CREDITO;80,00;2,00""";

        exchange = new DefaultExchange(new DefaultCamelContext());
        message.setBody(new ByteArrayInputStream(arquivoPagamentoGateway.getBytes(StandardCharsets.UTF_8)));
        exchange.setIn(message);

        fields = new String[]{"002", "06082023", "123456*1234", "AAA001", "7965", "VISA", "3", "CREDITO", "100,00", "2,00"};

    }

    @Test
    @DisplayName("Test File Reader Process")
    public void testFileReaderProcess() {
        //Given
        when(message.getBody(InputStream.class)).thenReturn(new ByteArrayInputStream(arquivoPagamentoGateway.getBytes(StandardCharsets.UTF_8)));

        //When
        fileReader.process(exchange);

        //Then
        verify(producer, times(4)).enviar(any());
    }

    @Test
    @DisplayName("Test Process Content Fields File")
    public void testProcessContentFieldsFile() {
        //Given
        when(fileReader.convertContentToVendaDetailDto(fields)).thenReturn(any(VendaDetailDto.class));

        //When
        fileReader.processContent(fields);

        //Then
        verify(producer, times(1)).enviar(any());
    }

    @Test
    @DisplayName("Test Process Content Fields File Throws Exception")
    public void testConvertContentToVendaDetailDtoThrowsException() {
        //Given
        String[] line = new String[]{"002", "data-invalida", "123456*1234", "AAA001", "7965", "VISA", "3", "CREDITO", "100,00", "2,00"};

        //When
        RuntimeException exception = assertThrows(RuntimeException.class, () -> fileReader.convertContentToVendaDetailDto(line));

        //Then
        assertTrue(exception.getMessage().contains("Erro ao converter para VendaDetailDto"));
    }

    @Test
    @DisplayName("Test Process Content Fields File Exception")
    public void testConvertContentToVendaDetailDtoException() {
        //When
        VendaDetailDto vendaDetailDto = fileReader.convertContentToVendaDetailDto(fields);

        //Then
        assertNotNull(vendaDetailDto);
        assertEquals("123456*1234", vendaDetailDto.cartao());
        assertEquals("AAA001", vendaDetailDto.codigoAutorizacao());
        assertEquals("VISA", vendaDetailDto.bandeira());
    }

    @Test
    @DisplayName("Test Convert String To Date")
    public void testConverStringToDate() throws ParseException  {
        //Given
        SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
        String stringDate = "06082023";

        //When
        Date date = fileReader.stringToDate(stringDate);

        //Then
        assertEquals(formatter.parse(stringDate), date);
    }

}