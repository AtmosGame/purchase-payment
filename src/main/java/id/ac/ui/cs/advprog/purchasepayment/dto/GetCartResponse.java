package id.ac.ui.cs.advprog.purchasepayment.dto;

import id.ac.ui.cs.advprog.purchasepayment.models.Cart;
import id.ac.ui.cs.advprog.purchasepayment.models.CartDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetCartResponse {

    private Integer id;
    private String username;
    private List<CartDetailsData> cartDetailsData;

    public static GetCartResponse fromCart(Cart cart, List<CartDetails> orderDetails) {
        if (cart == null) {
            return null;
        }

        return GetCartResponse.builder()
                .id(cart.getId())
                .username(cart.getUsername())
                .cartDetailsData(orderDetails
                        .stream()
                        .map(CartDetailsData::fromCartDetails)
                        .collect(Collectors.toList()))
                .build();
    }


}
