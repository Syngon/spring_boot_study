package br.gov.ba.prodeb.teste.name.client;

import br.gov.ba.prodeb.teste.name.DTO.AddressDTO;
import feign.Param;
import feign.RequestLine;

public interface ViaCepFeign {
    @RequestLine("GET /ws/{cep}/json/")
    AddressDTO getAddressByCep(@Param String cep);
}




