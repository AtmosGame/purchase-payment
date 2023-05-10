package id.ac.ui.cs.advprog.purchasepayment.ports;

import id.ac.ui.cs.advprog.purchasepayment.models.SecretToken;
import org.springframework.data.repository.CrudRepository;


public interface SecretTokenRepository extends CrudRepository<SecretToken, Integer> {
    boolean existsByTokenName(String tokenName);
}
