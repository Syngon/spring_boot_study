package br.gov.ba.prodeb.teste.name.exceptions;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException() {
        super("Cliente nao encontrado");
    }
}

