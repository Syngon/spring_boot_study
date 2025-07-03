package br.gov.ba.prodeb.teste.name.resources;

import br.gov.ba.prodeb.teste.name.DTO.AddressDTO;
import br.gov.ba.prodeb.teste.name.client.ViaCepFeign;
import br.gov.ba.prodeb.teste.name.entities.Customer;
import br.gov.ba.prodeb.teste.name.repositories.CustomerRepository;
import br.gov.ba.prodeb.teste.name.requests.CustomerRequest;
import feign.Feign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<?> customerById(@PathVariable String id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isEmpty()) {
            return new ResponseEntity<>("Cliente nao encontrado", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(customer);
    }

    @GetMapping("/customers/name/{name}")
    public Customer customerByName(@PathVariable String name) {
        return customerRepository.findByName(name);
    }

    @GetMapping("/customers/state/{state}")
    public List<Customer> customersByState(@PathVariable String state) {
        return customerRepository.findByAddressState(state);
    }
}
