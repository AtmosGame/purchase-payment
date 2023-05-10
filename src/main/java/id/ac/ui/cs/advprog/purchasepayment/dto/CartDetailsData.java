package id.ac.ui.cs.advprog.purchasepayment.dto;

import id.ac.ui.cs.advprog.purchasepayment.models.CartDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartDetailsData {
    private Integer id;
    private Date addDate;
    private String appId;
    private String appName;
    private double appPrice;

    public static CartDetailsData fromCartDetails(CartDetails cartDetails) {
        return CartDetailsData.builder()
                .id(cartDetails.getId())
                .addDate(cartDetails.getAddDate())
                .appId(cartDetails.getAppId())
                .appName(cartDetails.getAppName())
                .appPrice(cartDetails.getAppPrice())
                .build();
    }
}
