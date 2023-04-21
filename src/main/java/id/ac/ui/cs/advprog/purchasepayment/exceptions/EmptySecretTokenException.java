package id.ac.ui.cs.advprog.purchasepayment.exceptions;

public class EmptySecretTokenException extends RuntimeException {

    public EmptySecretTokenException() {
        super("Token cannot be empty");
    }
}