package id.ac.ui.cs.advprog.purchasepayment.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SecretTokenInvalidException extends RuntimeException{
    public SecretTokenInvalidException(String token) {
        super(String.format("Secret token %s invalid", token));
    }
}
