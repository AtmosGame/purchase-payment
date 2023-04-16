package id.ac.ui.cs.advprog.purchasepayment.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AppAlreadyInCartException extends RuntimeException{
    public AppAlreadyInCartException(String appName, String appId) {
        super(String.format("App %s with id:%s already exist in cart", appName, appId));
    }
}
