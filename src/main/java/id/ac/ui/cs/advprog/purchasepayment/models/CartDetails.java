package id.ac.ui.cs.advprog.purchasepayment.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cart_details")
public class CartDetails {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(nullable = false)
    private String appId;
    @Column(nullable = false)
    private String appName;
    @Column(nullable = false)
    private Double appPrice;

    private Date addDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="cart_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Cart cart;
}
