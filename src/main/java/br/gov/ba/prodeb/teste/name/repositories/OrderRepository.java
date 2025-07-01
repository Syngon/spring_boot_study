package br.gov.ba.prodeb.teste.name.repositories;

import br.gov.ba.prodeb.teste.name.entities.Order;
import br.gov.ba.prodeb.teste.name.enums.OrderStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {
    Order findByDescription(String description);
    Order findById(Long id);
    Order findByCustomerId(Long customerId);
    List<Order> findAllByCustomerId(Long customerId);
    List<Order> findByPriceGreaterThan(Integer price);
    List<Order> findByStatus(OrderStatus status);
}