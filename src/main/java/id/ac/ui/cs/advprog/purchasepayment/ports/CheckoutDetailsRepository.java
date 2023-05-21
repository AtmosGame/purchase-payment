package id.ac.ui.cs.advprog.purchasepayment.ports;

import id.ac.ui.cs.advprog.purchasepayment.models.CartDetails;
import id.ac.ui.cs.advprog.purchasepayment.models.CheckoutDetails;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CheckoutDetailsRepository extends CrudRepository<CheckoutDetails, Integer> {
    boolean existsByCheckoutUsernameAndAppId(String username, String appId);
}