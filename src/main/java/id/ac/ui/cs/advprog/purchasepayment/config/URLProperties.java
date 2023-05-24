package id.ac.ui.cs.advprog.purchasepayment.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "url")
public class URLProperties {
    private String purchasePayment;
    private String authAdmin;
    private String gamesAppsStore;

    public String getPurchasePaymentURL() {
        return purchasePayment;
    }

    public void setPurchasePayment(String purchasePayment) {
        this.purchasePayment = purchasePayment;
    }

    public String getAuthAdminURL() {
        return authAdmin;
    }

    public void setAuthAdmin(String authAdmin) {
        this.authAdmin = authAdmin;
    }

    public String getGamesAppsStoreURL() {
        return gamesAppsStore;
    }

    public void setGamesAppsStore(String gamesAppsStore) {
        this.gamesAppsStore = gamesAppsStore;
    }
}
