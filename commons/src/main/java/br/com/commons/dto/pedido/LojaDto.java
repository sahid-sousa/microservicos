package br.com.commons.dto.pedido;

import java.io.Serial;
import java.io.Serializable;

public record LojaDto (
        String uuid,
        String codigo,
        String cnpj
) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
}