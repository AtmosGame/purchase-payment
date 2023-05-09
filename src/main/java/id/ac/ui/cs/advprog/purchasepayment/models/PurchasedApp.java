package id.ac.ui.cs.advprog.purchasepayment.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "purchased_app")
public class PurchasedApp {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String appId;

    @Column(nullable = false)
    private String appName;

    @Column(nullable = false)
    private Double appPrice;

    @Column(nullable = false)
    private Date purchasedDate;
}
