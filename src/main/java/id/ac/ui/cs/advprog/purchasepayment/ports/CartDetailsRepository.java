package id.ac.ui.cs.advprog.purchasepayment.ports;

import id.ac.ui.cs.advprog.purchasepayment.models.CartDetails;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CartDetailsRepository extends CrudRepository<CartDetails, Integer> {
    List<CartDetails> findAllByCartId(Integer cartId);
    List<CartDetails> findByCartUsername(String username);
    Optional<CartDetails> findByCartUsernameAndAppId(String username, String appId);
    @Transactional
    void deleteByCartId(Integer cartId);
}
