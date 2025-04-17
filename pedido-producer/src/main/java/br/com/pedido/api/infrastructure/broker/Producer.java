package br.com.pedido.api.infrastructure.broker;

public interface Producer<T>{
    void enviar(T t);
}