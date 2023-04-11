package id.ac.ui.cs.advprog.purchasepayment.ports;

import id.ac.ui.cs.advprog.purchasepayment.models.Cart;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CartRepository extends CrudRepository<Cart, Integer> {
    Optional<Cart> findByUsername(String username);
}
