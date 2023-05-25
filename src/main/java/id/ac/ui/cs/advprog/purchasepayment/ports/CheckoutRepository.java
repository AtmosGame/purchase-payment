package id.ac.ui.cs.advprog.purchasepayment.ports;

import id.ac.ui.cs.advprog.purchasepayment.models.Checkout;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CheckoutRepository extends CrudRepository<Checkout, Integer>{
    Optional<Checkout> findByUsername(String username);

    Optional<Checkout> findById(Integer id);

    boolean existsById(Integer id);
}
