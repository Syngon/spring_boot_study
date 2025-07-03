package br.gov.ba.prodeb.teste.name.requests;

import br.gov.ba.prodeb.teste.name.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private String description;
    private Integer price;
    private OrderStatus status;
    private String customerId;

}
