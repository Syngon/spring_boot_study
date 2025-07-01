package br.gov.ba.prodeb.teste.name.order;

import br.gov.ba.prodeb.teste.name.customer.Customer;
import br.gov.ba.prodeb.teste.name.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping("/orders")
    public ResponseEntity<?> createOrder(@RequestBody Order order) {
        if (customerRepository.findById(order.getCustomerId()) == null) {
            return new ResponseEntity<>("Usuario nao encontrado", HttpStatus.NOT_FOUND);
        }

        Order createdOrder = orderRepository.save(order);
        Order createdOrderWithCustomer =  this.setOrderCustomer(createdOrder);
        return ResponseEntity.ok(createdOrderWithCustomer);
    }

    @GetMapping("/orders")
    public ResponseEntity<?> orders() {
        List<Order> orders = orderRepository
                .findAll()
                .stream()
                .map(this::setOrderCustomer)
                .toList();

        return ResponseEntity.ok(orders);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<?>  orderById(@PathVariable Long id) {
        Order order = orderRepository.findById(id);
        Order orderWithCustomer = this.setOrderCustomer(order);
        return ResponseEntity.ok(orderWithCustomer);
    }

    @GetMapping("/orders/customer/{customerId}")
    public ResponseEntity<?> ordersByCustomerName(@PathVariable Long customerId) {
        List<Order> orderList =  orderRepository
                .findAllByCustomerId(customerId)
                .stream()
                .map(this::setOrderCustomer)
                .toList();

        return ResponseEntity.ok(orderList);
    }

@GetMapping("/orders/customer/{customerId}/details")
public ResponseEntity<?> orderDetailsById(@PathVariable Long customerId) {
    List<Order> orders = orderRepository.findAllByCustomerId(customerId);

    if (orders.isEmpty()) {
        return new ResponseEntity<>("Nenhuma compra encontrada para esse usuario", HttpStatus.NOT_FOUND);
    }

    int totalOrders = orders.size();
    int totalPrice =
                orders
                .stream()
                .mapToInt(order -> order.getPrice() != null ? order.getPrice() : 0)
                .sum();

    Map<String, Object> response = Map.of(
            "customerId", customerId,
            "totalPrice", totalPrice,
            "totalOrders", totalOrders,
            "orders", orders.stream().map(this::setOrderCustomer).toList(),
            "customer", customerRepository.findById(customerId)
    );

    return ResponseEntity.ok(response);
}

    private Order setOrderCustomer(Order order) {
        Customer customer = customerRepository.findById(order.getCustomerId());
        return order.setCustomer(customer);
    }
}
