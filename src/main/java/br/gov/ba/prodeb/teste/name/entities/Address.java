package br.gov.ba.prodeb.teste.name.entities;

import br.gov.ba.prodeb.teste.name.DTO.AddressDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Address {
    private String street;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    private String additionalInfo;
    private String neighborhood;

    public Address(AddressDTO addressDTO) {
        this.street = addressDTO.getStreet();
        this.city = addressDTO.getCity();
        this.state = addressDTO.getState();
        this.zipCode = addressDTO.getZipCode();
        this.country = addressDTO.getCountry();
        this.additionalInfo = addressDTO.getAdditionalInfo();
        this.neighborhood = addressDTO.getNeighborhood();
    }
}
