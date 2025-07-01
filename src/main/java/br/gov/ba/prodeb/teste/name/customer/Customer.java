package br.gov.ba.prodeb.teste.name.customer;

import lombok.Getter;
import lombok.Setter;

public class Customer {
    private @Getter @Setter Long id;
    private @Getter @Setter String name;
    private @Getter @Setter String email;
    private @Getter @Setter String phone;
    private @Getter @Setter Address address;

    public Customer(Long id, String name, String email, String phone, Address address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }
}
