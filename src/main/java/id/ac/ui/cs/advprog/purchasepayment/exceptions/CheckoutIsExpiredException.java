package id.ac.ui.cs.advprog.purchasepayment.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CheckoutIsExpiredException extends RuntimeException {
    public CheckoutIsExpiredException(String id) {
        super(String.format("Checkout with %s already expired", id));
    }
}
