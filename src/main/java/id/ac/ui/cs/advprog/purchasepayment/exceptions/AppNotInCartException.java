package id.ac.ui.cs.advprog.purchasepayment.exceptions;

public class AppNotInCartException extends RuntimeException {
    public AppNotInCartException(String appId) {
        super(String.format("App with id:%s does not exist in cart", appId));
    }
}
