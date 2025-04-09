package br.com.pedido.interfaces.dto;

import java.io.Serializable;

public record LojaDto (
        String codigo,
        String cnpj
) implements Serializable {
    private static final long serialVersionUID = 1L;
}

