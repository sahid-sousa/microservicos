package br.com.transacao.application.usecases.calendario;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@SpringBootTest
@ActiveProfiles("test")
public class DiaUtilTest {

    @Autowired
    private DiaUtil diaUtil;

    @Test
    void deveRetonarVerdadeiroParaDiaUtil() throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        Calendar cal = Calendar.getInstance();
        cal.setTime(formatter.parse("06/05/2025"));

        Boolean resultado = diaUtil.ehDiaUtil(cal);

        Assertions.assertTrue(resultado);

    }

    @Test
    void deveRetonarFalsoParaDiaNaoUtil() throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        Calendar cal = Calendar.getInstance();
        cal.setTime(formatter.parse("10/05/2025"));

        Boolean resultado = diaUtil.ehDiaUtil(cal);

        Assertions.assertFalse(resultado);

    }

    @Test
    void deveRetonarDiaUtil() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date data = formatter.parse("06/05/2025");

        Date util = diaUtil.diasUteis(data, 30);

        Calendar cal = Calendar.getInstance();
        cal.setTime(util);

        Boolean resultado = diaUtil.ehDiaUtil(cal);

        Assertions.assertTrue(resultado);
    }


}
