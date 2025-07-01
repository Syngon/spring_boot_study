package br.gov.ba.prodeb.teste.name.order;

import br.gov.ba.prodeb.teste.name.customer.Customer;
import lombok.Getter;
import lombok.Setter;

public class Order {
    private @Getter @Setter Long id;
    private @Getter @Setter String description;
    private @Getter @Setter Integer price;
    private @Getter @Setter String status;
    private @Getter @Setter Long customerId;
    private @Getter Customer customer;

    public Order(Long id, String description, Integer price, String status, Long customerId) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.status = status;
        this.customerId = customerId;
    }

    public Order setCustomer(Customer customer) {
        this.customer = customer;
        return this;
    }
}
