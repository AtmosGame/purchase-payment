package id.ac.ui.cs.advprog.purchasepayment.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "checkout")
public class Checkout {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private String statusPembayaran;

    @Column(unique = true, nullable = false)
    private String username;

    @Column
    private LocalDateTime waktuPembuatanCheckout;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="cart_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Cart cart;
}
