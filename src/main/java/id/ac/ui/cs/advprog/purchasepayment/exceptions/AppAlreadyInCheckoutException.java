package id.ac.ui.cs.advprog.purchasepayment.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AppAlreadyInCheckoutException extends RuntimeException{
    public AppAlreadyInCheckoutException(String appName, String appId) {
        super(String.format("App %s with id:%s already in checkout process", appName, appId));
    }
}
