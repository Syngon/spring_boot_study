package br.gov.ba.prodeb.teste.name.resources;

import br.gov.ba.prodeb.teste.name.entities.Customer;
import br.gov.ba.prodeb.teste.name.entities.Order;
import br.gov.ba.prodeb.teste.name.enums.OrderStatus;
import br.gov.ba.prodeb.teste.name.repositories.CustomerRepository;
import br.gov.ba.prodeb.teste.name.repositories.OrderRepository;
import br.gov.ba.prodeb.teste.name.requests.OrderRequest;
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
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest body) {
        if (customerRepository.findById(body.getCustomerId()) == null) {
            return new ResponseEntity<>("Usuario nao encontrado", HttpStatus.NOT_FOUND);
        }

        Order orderToSave = new Order(body);
        Order createdOrder = orderRepository.save(orderToSave);
        Order createdOrderWithCustomer =  this.setOrderCustomer(createdOrder);
        return ResponseEntity.ok(createdOrderWithCustomer);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> orders() {
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

    @GetMapping("/orders/products/price/{price}/gt")
    public ResponseEntity<List<Order>> productsMoreExpensiveThan(@PathVariable Integer price) {
        List<Order> orders = orderRepository.findByPriceGreaterThan(price)
                .stream()
                .map(this::setOrderCustomer)
                .toList();

        return ResponseEntity.ok(orders);
    }

    @GetMapping("/orders/status/{status}")
    public ResponseEntity<List<Order>> ordersByStatus(@PathVariable String status) {
        OrderStatus orderStatus = OrderStatus.valueOf(status.toUpperCase());
        List<Order> orders = orderRepository.findByStatus(orderStatus)
                .stream()
                .map(this::setOrderCustomer)
                .toList();

        return ResponseEntity.ok(orders);
    }

    private Order setOrderCustomer(Order order, Customer customer) {
        return order.setCustomer(customer);
    }
    private Order setOrderCustomer(Order order) {
        Customer customer = customerRepository.findById(order.getCustomerId());
        return order.setCustomer(customer);
    }
}
