package br.gov.ba.prodeb.teste.name.order;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {
    Order findByDescription(String description);
    Order findById(Long id);
    Order findByCustomerId(Long customerId);
    List<Order> findAllByCustomerId(Long customerId);
}