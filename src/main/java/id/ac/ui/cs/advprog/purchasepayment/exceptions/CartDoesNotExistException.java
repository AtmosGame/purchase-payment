package id.ac.ui.cs.advprog.purchasepayment.exceptions;

public class CartDoesNotExistException extends RuntimeException {

    public CartDoesNotExistException(String username) {
        super("Cart with username " + username + " does not exist");
    }
}