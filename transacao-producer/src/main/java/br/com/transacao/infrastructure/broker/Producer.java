package br.com.transacao.infrastructure.broker;

public interface Producer<T> {
    void enviar(T t);
}