package id.ac.ui.cs.advprog.purchasepayment.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AppAlreadyInListException extends RuntimeException{
    public AppAlreadyInListException(String appName, String appId) {
        super(String.format("App %s with id:%s already in user's bought list", appName, appId));
    }
}
