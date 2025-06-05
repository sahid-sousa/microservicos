package br.com.transacao.application.services.calendario;

import br.com.transacao.application.usecases.calendario.DiaUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Slf4j
@Service
public class DiaUtilImpl implements DiaUtil {

    @Override
    public Boolean ehDiaUtil(Calendar cal) {
        int diaSemana = cal.get(Calendar.DAY_OF_WEEK);
        return diaSemana != Calendar.SATURDAY && diaSemana != Calendar.SUNDAY;
    }

    @Override
    public Date diasUteis(Date data, int dias) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        int adicionados = 0;

        while (adicionados < dias) {
            cal.add(Calendar.DAY_OF_MONTH, 1);
            if (ehDiaUtil(cal)) {
                adicionados++;
            }
        }

        // Garantir que o dia final também é útil
        while (!ehDiaUtil(cal)) {
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }

        return cal.getTime();
    }

}
