package id.ac.ui.cs.advprog.purchasepayment.ports;

import id.ac.ui.cs.advprog.purchasepayment.models.Cart;
import id.ac.ui.cs.advprog.purchasepayment.models.SecretToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SecretTokenRepository extends CrudRepository<SecretToken, Integer> {
    boolean existsByTokenName(String tokenName);
}
