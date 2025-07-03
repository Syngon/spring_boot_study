package br.gov.ba.prodeb.teste.name.exceptions;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException() {
        super("Compra nao encontrada");
    }
}

