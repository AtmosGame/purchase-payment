package id.ac.ui.cs.advprog.purchasepayment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCartRequest {
    private String id;
    private String name;
    private Double price;
    private String username;
}
