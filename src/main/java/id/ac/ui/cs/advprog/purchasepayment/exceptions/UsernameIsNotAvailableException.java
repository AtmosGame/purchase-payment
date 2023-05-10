package id.ac.ui.cs.advprog.purchasepayment.exceptions;

import lombok.NoArgsConstructor;


public class UsernameIsNotAvailableException extends RuntimeException{
    public UsernameIsNotAvailableException() {
        super(String.format("Username is not available"));
    }
}
