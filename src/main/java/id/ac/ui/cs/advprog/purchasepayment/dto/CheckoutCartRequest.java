package id.ac.ui.cs.advprog.purchasepayment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutCartRequest {
    private String id;
    private String name;
    private Double price;
    private String username;
}
