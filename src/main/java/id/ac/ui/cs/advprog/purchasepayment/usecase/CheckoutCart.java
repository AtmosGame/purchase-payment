package id.ac.ui.cs.advprog.purchasepayment.usecase;

import id.ac.ui.cs.advprog.purchasepayment.dto.CheckoutCartRequest;
import id.ac.ui.cs.advprog.purchasepayment.models.Checkout;

public interface CheckoutCart {
    Checkout checkout(CheckoutCartRequest request);
    Checkout getCheckoutById(Integer checkoutId);
}
