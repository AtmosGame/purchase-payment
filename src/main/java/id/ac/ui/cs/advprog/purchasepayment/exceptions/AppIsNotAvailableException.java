package id.ac.ui.cs.advprog.purchasepayment.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AppIsNotAvailableException extends RuntimeException{
    public AppIsNotAvailableException(String appId) {
        super(String.format("App with id %s is not available", appId));
    }
}
