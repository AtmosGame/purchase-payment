package id.ac.ui.cs.advprog.purchasepayment.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "purchased-app")
public class PurchasedApp {
    @Id
    @GeneratedValue
    private Integer id;

//    @OneToOne(fetch = FetchType.EAGER)
    private String userId;

//    @OneToOne(fetch = FetchType.EAGER)
    private String appId;
}
