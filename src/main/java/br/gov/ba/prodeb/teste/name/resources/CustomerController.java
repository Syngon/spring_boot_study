package br.gov.ba.prodeb.teste.name.resources;

import br.gov.ba.prodeb.teste.name.DTO.AddressDTO;
import br.gov.ba.prodeb.teste.name.client.ViaCepFeign;
import br.gov.ba.prodeb.teste.name.entities.Customer;
import br.gov.ba.prodeb.teste.name.repositories.CustomerRepository;
import br.gov.ba.prodeb.teste.name.requests.CustomerRequest;
import feign.Feign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping("/customers")
    public Customer createCustomer(@RequestBody CustomerRequest body) {
        ViaCepFeign viaCepFeign = Feign.builder()
                .decoder(new feign.jackson.JacksonDecoder())
                .target(ViaCepFeign.class, "https://viacep.com.br");

        AddressDTO addressDTO = viaCepFeign.getAddressByCep(body.getZipCode());
        Customer customer = new Customer(body, addressDTO);

        customerRepository.save(customer);
        return customer;
    }

    @GetMapping("/customers")
    public List<Customer> customers() {
        return customerRepository.findAll();
    }

    @GetMapping("/customers/{id}")
    public Customer customerById(@PathVariable Long id) {
        return customerRepository.findById(id);
    }

    @GetMapping("/customers/name/{name}")
    public Customer customerById(@PathVariable String name) {
        return customerRepository.findByName(name);
    }

    @GetMapping("/customers/state/{state}")
    public List<Customer> customersByState(@PathVariable String state) {
        return customerRepository.findByAddressState(state);
    }
}
