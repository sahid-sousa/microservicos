package br.com.transacao.application.services.calendario;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DiaUtilImplTest {

    @InjectMocks
    DiaUtilImpl diaUtil;

    @Test
    @DisplayName("Test Data E Dia Util")
    public void testDataEhDiaUtil() throws ParseException {
        //Given
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        cal.setTime(formatter.parse("08/05/2025"));

        //When
        Boolean ehDiaUtil = diaUtil.ehDiaUtil(cal);

        //Then
        assertTrue(ehDiaUtil);
    }


    @Test
    @DisplayName("Test Data nao e Dia Util")
    public void testDataNaoEhDiaUtil() throws ParseException {
        //Given
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        cal.setTime(formatter.parse("10/05/2025"));

        //When
        Boolean ehDiaUtil = diaUtil.ehDiaUtil(cal);

        //Then
        assertFalse(ehDiaUtil);
    }

    @Test
    @DisplayName("testDataApos1DiaEhDiaUtil")
    public void testDataApos1DiaEhDiaUtil() throws ParseException {
        //Given
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date data = formatter.parse("10/05/2025");
        Date dataApos30Dias = diaUtil.diasUteis(data, 1);
        cal.setTime(dataApos30Dias);

        //When
        Boolean ehDiaUtil = diaUtil.ehDiaUtil(cal);

        //Then
        assertTrue(ehDiaUtil);
    }


    @Test
    @DisplayName("testDataApos30DiasEhDiaUtil")
    public void testDataApos30DiasEhDiaUtil() throws ParseException {
        //Given
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date data = formatter.parse("10/05/2025");
        Date dataApos30Dias = diaUtil.diasUteis(data, 30);
        cal.setTime(dataApos30Dias);

        //When
        Boolean ehDiaUtil = diaUtil.ehDiaUtil(cal);

        //Then
        assertTrue(ehDiaUtil);
    }

    @Test
    @DisplayName("test Data Apos 1 Dias")
    public void testDataApos1Dia() throws ParseException {
        //Given
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date data = formatter.parse("07/05/2025");
        //When
        Date dataApos1Dia = diaUtil.diasUteis(data, 1);

        //Then
        assertEquals(formatter.parse("08/05/2025"), dataApos1Dia);
    }

    @Test
    @DisplayName("test Data Apos 30 Dias")
    public void testDataApos30Dias() throws ParseException {
        //Given
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date data = formatter.parse("07/05/2025");
        //When
        Date dataApos30Dias = diaUtil.diasUteis(data, 30);

        //Then
        assertEquals(formatter.parse("18/06/2025"), dataApos30Dias);
    }

    @Test
    @DisplayName("test Data Apos 60 Dias")
    public void testDataApos60Dias() throws ParseException {
        //Given
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date data = formatter.parse("07/05/2025");
        //When
        Date dataApos60Dias = diaUtil.diasUteis(data, 60);

        //Then
        assertEquals(formatter.parse("30/07/2025"), dataApos60Dias);
    }

}