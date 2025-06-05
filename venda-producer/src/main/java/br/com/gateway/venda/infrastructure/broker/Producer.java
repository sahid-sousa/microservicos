package br.com.gateway.venda.infrastructure.broker;

public interface Producer<T>{
    void enviar(T t);
}
