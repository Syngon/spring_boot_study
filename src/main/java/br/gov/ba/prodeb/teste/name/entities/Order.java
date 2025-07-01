package br.gov.ba.prodeb.teste.name.entities;

import br.gov.ba.prodeb.teste.name.enums.OrderStatus;
import br.gov.ba.prodeb.teste.name.requests.OrderRequest;
import jakarta.persistence.EntityListeners;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor()
@NotNull
@Document("order")
@EntityListeners(AuditingEntityListener.class)
public class Order {
    @Id
    private String id;
    private String description;
    private Integer price;
    private OrderStatus status;
    private Long customerId;
    private @Null Customer customer;
    @CreatedDate
    private Instant createdAt;

    public Order(OrderRequest orderRequest) {
        this.description = orderRequest.getDescription();
        this.price = orderRequest.getPrice();
        this.status = orderRequest.getStatus();
        this.customerId = orderRequest.getCustomerId();
    }

    public Order setCustomer(Customer customer) {
        this.customer = customer;
        return this;
    }
}
