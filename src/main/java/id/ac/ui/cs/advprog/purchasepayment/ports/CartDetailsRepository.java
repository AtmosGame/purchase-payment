package id.ac.ui.cs.advprog.purchasepayment.ports;

import id.ac.ui.cs.advprog.purchasepayment.models.CartDetails;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CartDetailsRepository extends CrudRepository<CartDetails, Integer> {
    List<CartDetails> findAllByCartId(Integer cartId);
    boolean existsByCartUsernameAndAppId(String username, String appId);
    @Transactional
    void deleteByCartUsername(String username);
    @Transactional
    Integer deleteCartDetailsByCartUsernameAndAppId(String username, String appId);
}
