package br.gov.ba.prodeb.teste.name.customer;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends MongoRepository<Customer, String> {
    Customer findByName(String name);
    Customer findById(Long id);
}
