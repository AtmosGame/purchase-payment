package id.ac.ui.cs.advprog.purchasepayment.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UsernameIsNotAvailableException extends RuntimeException{
    public UsernameIsNotAvailableException(String username) {
        super(String.format("User with the username %s is not available", username));
    }
}
