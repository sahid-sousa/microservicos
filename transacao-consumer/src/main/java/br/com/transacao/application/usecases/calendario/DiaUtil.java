package br.com.transacao.application.usecases.calendario;

import java.util.Calendar;
import java.util.Date;

public interface DiaUtil {
    Boolean ehDiaUtil(Calendar cal);
    Date diasUteis(Date data, int dias);
}
