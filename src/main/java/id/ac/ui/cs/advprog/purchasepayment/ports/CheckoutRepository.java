package id.ac.ui.cs.advprog.purchasepayment.ports;

import id.ac.ui.cs.advprog.purchasepayment.models.Checkout;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CheckoutRepository extends CrudRepository<Checkout, Integer>{
    Optional<Checkout> findByUsername(String username);

    Optional<Checkout> findById(Integer id);

    boolean existsById(Integer id);

    @Query(value = """
        SELECT CASE
                   WHEN COUNT(*) > 0 THEN TRUE
                   ELSE FALSE
                   END
        FROM checkout c
                 JOIN checkout_details cd ON c.id = cd.checkout_id
        WHERE c.status_pembayaran = 'Menunggu Pembayaran'
          AND c.username = ?1
          AND cd.app_id = ?2
    """, nativeQuery = true)
    boolean existsAppInUserActiveCheckout(String username, String app_id);
}
