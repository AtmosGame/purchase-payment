package id.ac.ui.cs.advprog.purchasepayment.usecase;

import id.ac.ui.cs.advprog.purchasepayment.dto.CheckoutCartRequest;
import id.ac.ui.cs.advprog.purchasepayment.models.Cart;
import id.ac.ui.cs.advprog.purchasepayment.models.CartDetails;

import java.util.Optional;

public interface CheckoutCart {
    Cart checkout(CheckoutCartRequest request);
    Cart getCartByUsername(String username);
    CartDetails addCartDetailsToCartByRequest(CheckoutCartRequest request, Cart cart);
    Optional<Cart> findCartByUsername(String username);
    Optional<CartDetails> findCartDetailsByCartUsernameAndAppId(String username, String appId);
    boolean isAppNotInCart(CheckoutCartRequest request);

}
