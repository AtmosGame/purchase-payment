package id.ac.ui.cs.advprog.purchasepayment.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AppAlreadyInPurchasedAppException extends RuntimeException{
    public AppAlreadyInPurchasedAppException(String appName, String appId) {
        super(String.format("App %s with id:%s already in user's purchased app", appName, appId));
    }
}
