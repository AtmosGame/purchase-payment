package id.ac.ui.cs.advprog.purchasepayment.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CheckoutIsActiveException extends RuntimeException {
    public CheckoutIsActiveException(String id) {
        super(String.format("Checkout with %s not in active/waiting state", id));
    }
}
