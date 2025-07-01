package br.gov.ba.prodeb.teste.name.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressDTO {

    @JsonProperty("logradouro")
    private String street;
    @JsonProperty("localidade")
    private String city;
    @JsonProperty("uf")
    private String state;
    @JsonProperty("cep")
    private String zipCode;
    private String country = "Brasil";
    @JsonProperty("complemento")
    private String additionalInfo;
    @JsonProperty("bairro")
    private String neighborhood;
    @JsonProperty("regiao")
    private String region;
}

