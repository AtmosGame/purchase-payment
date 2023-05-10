package id.ac.ui.cs.advprog.purchasepayment.exceptions;

public class CartIsEmptyException extends RuntimeException {

    public CartIsEmptyException(String username) {
        super("Cart with username " + username + " is empty");
    }
}
