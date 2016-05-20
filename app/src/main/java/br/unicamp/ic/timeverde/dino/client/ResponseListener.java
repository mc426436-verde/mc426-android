package br.unicamp.ic.timeverde.dino.client;


public interface ResponseListener<T> {
    T onSuccess();
    void onFailure();
}
