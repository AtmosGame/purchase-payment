package id.ac.ui.cs.advprog.purchasepayment.usecase;

import id.ac.ui.cs.advprog.purchasepayment.annotations.UseCase;
import id.ac.ui.cs.advprog.purchasepayment.dto.CheckoutCartRequest;
import id.ac.ui.cs.advprog.purchasepayment.exceptions.AppAlreadyInCartException;
import id.ac.ui.cs.advprog.purchasepayment.models.Cart;
import id.ac.ui.cs.advprog.purchasepayment.models.CartDetails;
import id.ac.ui.cs.advprog.purchasepayment.ports.CartDetailsRepository;
import id.ac.ui.cs.advprog.purchasepayment.ports.CartRepository;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.Optional;

@UseCase
@RequiredArgsConstructor
public class CheckoutCartImpl implements CheckoutCart {
    private final CartRepository cartRepository;
    private final CartDetailsRepository cartDetailsRepository;

    @Override
    public Cart checkout(CheckoutCartRequest request) {
        var userCart = getCartByUsername(request.getUsername());
        addCartDetailsToCartByRequest(request, userCart);
        return userCart;
    }

    @Override
    public Cart getCartByUsername(String username) {
        Optional<Cart> optionalUserCart = findCartByUsername(username);
        return optionalUserCart.orElseGet(() -> {
            var userCart = Cart.builder().username(username).build();
            return cartRepository.save(userCart);
        });
    }

    @Override
    public Optional<Cart> findCartByUsername(String username) {
        return cartRepository.findByUsername(username);
    }

    @Override
    public CartDetails addCartDetailsToCartByRequest(CheckoutCartRequest request, Cart cart) {
        if (isAppNotInCart(request)) {
            var cartDetails = CartDetails.builder()
                    .appId(request.getId())
                    .appName(request.getName())
                    .addDate(new Date())
                    .appPrice(request.getPrice())
                    .cart(cart)
                    .build();
            return cartDetailsRepository.save(cartDetails);
        } else {
            throw new AppAlreadyInCartException(request.getName(), request.getId());
        }
    }

    @Override
    public Optional<CartDetails> findCartDetailsByCartUsernameAndAppId(String username, String appId) {
        return cartDetailsRepository.findByCartUsernameAndAppId(username, appId);
    }

    @Override
    public boolean isAppNotInCart(CheckoutCartRequest request) {
        return findCartDetailsByCartUsernameAndAppId(request.getUsername(), request.getId()).isEmpty();
    }
}
