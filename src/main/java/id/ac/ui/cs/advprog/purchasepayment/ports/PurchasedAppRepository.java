package id.ac.ui.cs.advprog.purchasepayment.ports;

import id.ac.ui.cs.advprog.purchasepayment.models.PurchasedApp;
import org.springframework.data.repository.CrudRepository;

public interface PurchasedAppRepository extends CrudRepository<PurchasedApp, Integer> {
    boolean existsPurchasedAppByAppId(String id);
    boolean existsPurchasedAppByUsername(String id);

    boolean existsByUsernameAndAppId(String username, String appId);
}
