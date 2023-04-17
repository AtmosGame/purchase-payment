package id.ac.ui.cs.advprog.purchasepayment.usecase;

import id.ac.ui.cs.advprog.purchasepayment.dto.UpdateCartRequest;
import id.ac.ui.cs.advprog.purchasepayment.models.Cart;
import id.ac.ui.cs.advprog.purchasepayment.models.CartDetails;

import java.util.Optional;

public interface UpdateCart {
    Cart update(UpdateCartRequest request);
    Cart getCartByUsername(String username);
    Optional<Cart> findCartByUsername(String username);
    CartDetails addCartDetailsToCartByRequest(UpdateCartRequest request, Cart cart);
    Optional<CartDetails> findCartDetailsByCartUsernameAndAppId(String username, String appId);
    boolean isAppNotInCart(UpdateCartRequest request);
}
