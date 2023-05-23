package id.ac.ui.cs.advprog.purchasepayment.exceptions;


public class UsernameIsNotAvailableException extends RuntimeException{
    public UsernameIsNotAvailableException() {
        super("Username is not available");
    }
}
