package id.ac.ui.cs.advprog.purchasepayment.usecase.CheckPurchased;

import id.ac.ui.cs.advprog.purchasepayment.dto.CheckPurchasedRequest;

public interface CheckPurchasedApp {
    boolean isPurchased (CheckPurchasedRequest request);

//    Cart getCartByUsername(String username);
//    Optional<Cart> findCartByUsername(String username);
//    CartDetails addCartDetailsToCartByRequest(UpdateCartRequest request, Cart cart);
//    Optional<CartDetails> findCartDetailsByCartUsernameAndAppId(String username, String appId);
//    boolean isAppNotInCart(UpdateCartRequest request);
}
