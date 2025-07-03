package br.gov.ba.prodeb.teste.name.resources;

import br.gov.ba.prodeb.teste.name.entities.Customer;
import br.gov.ba.prodeb.teste.name.entities.Order;
import br.gov.ba.prodeb.teste.name.enums.OrderStatus;
import br.gov.ba.prodeb.teste.name.exceptions.CustomerNotFoundException;
import br.gov.ba.prodeb.teste.name.exceptions.OrderNotFoundException;
import br.gov.ba.prodeb.teste.name.repositories.CustomerRepository;
import br.gov.ba.prodeb.teste.name.repositories.OrderRepository;
import br.gov.ba.prodeb.teste.name.requests.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
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
        Customer customer = this.getCustomer(body.getCustomerId());

        Order orderToSave = new Order(body);
        Order createdOrder = orderRepository.save(orderToSave);
        Order createdOrderWithCustomer =  this.setOrderCustomer(createdOrder, customer);

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
    public ResponseEntity<?>  orderById(@PathVariable String id) {
        Order order = this.getOrder(id);
        Customer customer = this.getCustomer(order.getCustomerId());
        Order orderWithCustomer = this.setOrderCustomer(order, customer);

        return ResponseEntity.ok(orderWithCustomer);
    }

    @GetMapping("/orders/customer/{customerId}")
    public ResponseEntity<?> ordersByCustomerId(@PathVariable String customerId) {
        Customer customer = this.getCustomer(customerId); 

        List<Order> orderList =  orderRepository
                .findAllByCustomerId(customerId)
                .stream()
                .map(o -> this.setOrderCustomer(o, customer))
                .toList();

        return ResponseEntity.ok(orderList);
    }

    @GetMapping("/orders/customer/{customerId}/details")
    public ResponseEntity<?> orderDetailsById(@PathVariable String customerId) {
        List<Order> orders = orderRepository.findAllByCustomerId(customerId);
        Customer customer = this.getCustomer(customerId);
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
                "orders", orders.stream().map(o -> this.setOrderCustomer(o, customer)).toList(),
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

    @GetMapping("/orders/descriptions-and-prices/customer/{customerId}/desc")
    public ResponseEntity<List<Order>> descriptionsAndPricesByCustomerId(@PathVariable String customerId) {
        Customer customer = this.getCustomer(customerId);
        List<Order> orders = orderRepository.findDescriptionsAndPricesByCustomerIdOrderByPricesDesc(customerId)
                .stream()
                .map(o -> this.setOrderCustomer(o, customer))
                .toList();

        return ResponseEntity.ok(orders);
    }

    @GetMapping("/orders/status/{status}/customer/{customerId}")
    public ResponseEntity<List<Order>> ordersByStatusAndCustomer(@PathVariable String status, @PathVariable String customerId) {
        Customer customer = this.getCustomer(customerId);
        OrderStatus orderStatus = OrderStatus.valueOf(status.toUpperCase());
        List<Order> orders = orderRepository.findByStatusAndCustomerId(orderStatus, customerId)
                .stream()
                .map(o -> this.setOrderCustomer(o, customer))
                .toList();
    return ResponseEntity.ok(orders);
    }

    @GetMapping("/orders/customer/{customerId}/this-month")
    public ResponseEntity<List<Order>> ordersThisWeekByCustomer(@PathVariable String customerId) {
        Customer customer = this.getCustomer(customerId);
        List<Order> orders = orderRepository.findOrdersCreatedThisMonthByCustomer(customerId)
                .stream()
                .map(o -> this.setOrderCustomer(o, customer))
                .toList();

        return ResponseEntity.ok(orders);
    }

    private Customer getCustomer(String id) {
        return customerRepository.findById(id)
                .orElseThrow(CustomerNotFoundException::new);
    }

    private Order getOrder(String id) {
        return orderRepository.findById(id)
                .orElseThrow(OrderNotFoundException::new);
    }

    private Order setOrderCustomer(Order order, Customer customer) {
        return order.setCustomer(customer);
    }

    private Order setOrderCustomer(Order order) {
        Customer customer = this.getCustomer(order.getCustomerId());
        return this.setOrderCustomer(order, customer);
    }
}
