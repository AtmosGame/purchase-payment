package id.ac.ui.cs.advprog.purchasepayment.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "secret-token")
public class SecretToken {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(unique = true, nullable = false)
    private String tokenName;
}
