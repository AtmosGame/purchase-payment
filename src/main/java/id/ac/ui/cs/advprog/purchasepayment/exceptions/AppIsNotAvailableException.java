package id.ac.ui.cs.advprog.purchasepayment.exceptions;

import lombok.NoArgsConstructor;


public class AppIsNotAvailableException extends RuntimeException{
    public AppIsNotAvailableException() {
        super(String.format("App with this id is not available"));
    }
}
