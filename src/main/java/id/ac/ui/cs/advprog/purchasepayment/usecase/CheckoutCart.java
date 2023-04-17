package id.ac.ui.cs.advprog.purchasepayment.usecase;

import id.ac.ui.cs.advprog.purchasepayment.dto.CheckoutCartRequest;
import id.ac.ui.cs.advprog.purchasepayment.models.Cart;
import id.ac.ui.cs.advprog.purchasepayment.models.CartDetails;
import id.ac.ui.cs.advprog.purchasepayment.models.Checkout;

import java.util.Optional;

public interface CheckoutCart {
    Checkout checkout(CheckoutCartRequest request);
    Cart getCartByUsername(String username);
    Optional<Cart> findCartByUsername(String username);
    boolean checkCartIsEmpty(String username);
}
