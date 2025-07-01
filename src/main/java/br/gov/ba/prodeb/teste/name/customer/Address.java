package br.gov.ba.prodeb.teste.name.customer;

import lombok.Getter;
import lombok.Setter;

public class Address {
    private @Getter @Setter String street;
    private @Getter @Setter String city;
    private @Getter @Setter String state;
    private @Getter @Setter String zipCode;
    private @Getter @Setter String country;
    private @Getter @Setter String additionalInfo;
    private @Getter @Setter String neighborhood;

    public Address(String street, String city, String state, String zipCode, String country, String additionalInfo, String neighborhood) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.country = country;
        this.additionalInfo = additionalInfo;
        this.neighborhood = neighborhood;
    }

}
