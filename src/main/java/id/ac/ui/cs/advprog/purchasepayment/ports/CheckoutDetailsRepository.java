package id.ac.ui.cs.advprog.purchasepayment.ports;

import id.ac.ui.cs.advprog.purchasepayment.models.CheckoutDetails;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CheckoutDetailsRepository extends CrudRepository<CheckoutDetails, Integer> {
    boolean existsByCheckoutUsernameAndAppId(String username, String appId);

    List<CheckoutDetails> findAllByCheckoutId(Integer checkoutId);
}