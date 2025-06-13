package br.com.transacao.application.usecases.calendario;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class DiaUtilTest {

    @Mock
    private DiaUtil diaUtil;

    @Test
    @DisplayName("test Retonar VerdadeiroPara Dia Util")
    void testRetonarVerdadeiroParaDiaUtil() throws ParseException {
        //Given
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        given(diaUtil.ehDiaUtil(any())).willReturn(true);

        Calendar cal = Calendar.getInstance();
        cal.setTime(formatter.parse("06/05/2025"));

        //When
        Boolean resultado = diaUtil.ehDiaUtil(cal);

        //Then
        assertTrue(resultado);
    }

    @Test
    @DisplayName("test Retonar Falso Para Dia nao Util")
    void testRetonarFalsoParaDiaNaoUtil() throws ParseException {
        //Given
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        given(diaUtil.ehDiaUtil(any())).willReturn(false);

        Calendar cal = Calendar.getInstance();
        cal.setTime(formatter.parse("10/05/2025"));

        //When
        Boolean resultado = diaUtil.ehDiaUtil(cal);

        //Then
        assertFalse(resultado);
    }

    @Test
    @DisplayName("Test Retonar Dia Util")
    void testRetonarDiaUtil() throws ParseException {
        //Given
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date proximaDataUtil = formatter.parse("18/06/2025");

        given(diaUtil.diasUteis(any(), anyInt())).willReturn(proximaDataUtil);

        //When
        Date util = diaUtil.diasUteis(proximaDataUtil, 30);

        //Then
        assertEquals(util, proximaDataUtil);
    }


}
