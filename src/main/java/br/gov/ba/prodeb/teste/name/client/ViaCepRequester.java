package br.gov.ba.prodeb.teste.name.client;

import br.gov.ba.prodeb.teste.name.DTO.AddressDTO;
import feign.Feign;

public class ViaCepRequester {
    public static final String BASE_URL = "https://viacep.com.br";
    public static AddressDTO getAddressByCep(String cep) {
        return Feign.builder()
                .decoder(new feign.jackson.JacksonDecoder())
                .target(ViaCepFeign.class, BASE_URL).getAddressByCep(cep);
    }
}
