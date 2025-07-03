package br.gov.ba.prodeb.teste.name.repositories;

import br.gov.ba.prodeb.teste.name.entities.Order;
import br.gov.ba.prodeb.teste.name.enums.OrderStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {
    Order findByDescription(String description);
    Order findByCustomerId(String customerId);
    List<Order> findAllByCustomerId(String customerId);
    List<Order> findByPriceGreaterThan(Integer price);
    List<Order> findByStatus(OrderStatus status);
    List<Order> findByStatusAndCustomerId(OrderStatus status, String customerId);

    @Query(value = "{ 'customerId': ?0 }", fields = "{ 'description': 1, 'price': 1 }", sort = "{ 'price': -1 }")
    List<Order> findDescriptionsAndPricesByCustomerIdOrderByPricesDesc(String customerId);

    @Query(value = "{ 'customerId': ?0, $expr: { $and: [ { $eq: [ { $month: '$createdAt' }, { $month: '$$NOW' } ] }, { $eq: [ { $year: '$createdAt' }, { $year: '$$NOW' } ] } ] } }")
    List<Order> findOrdersCreatedThisMonthByCustomer(String customerId);
}