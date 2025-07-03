package br.gov.ba.prodeb.teste.name.repositories;

import br.gov.ba.prodeb.teste.name.entities.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CustomerRepository extends MongoRepository<Customer, String> {
    Customer findByName(String name);
    List<Customer> findByAddressState(String state);
}
