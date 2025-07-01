package br.gov.ba.prodeb.teste.name.entities;

import br.gov.ba.prodeb.teste.name.DTO.AddressDTO;
import br.gov.ba.prodeb.teste.name.requests.CustomerRequest;
import jakarta.persistence.EntityListeners;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("customer")
@EntityListeners(AuditingEntityListener.class)
public class Customer {
    @Id
    private String id;
    private String name;
    private String email;
    private String phone;
    private Address address;
    @CreatedDate
    private Instant createdAt;

    public Customer(CustomerRequest body, AddressDTO addressDTO) {
        this.name = body.getName();
        this.email = body.getEmail();
        this.phone = body.getPhone();
        this.address = new Address(addressDTO);
    }
}
