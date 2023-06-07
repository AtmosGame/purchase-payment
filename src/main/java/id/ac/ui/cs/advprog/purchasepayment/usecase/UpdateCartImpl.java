package id.ac.ui.cs.advprog.purchasepayment.usecase;

import id.ac.ui.cs.advprog.purchasepayment.annotations.UseCase;
import id.ac.ui.cs.advprog.purchasepayment.dto.UpdateCartRequest;
import id.ac.ui.cs.advprog.purchasepayment.models.Cart;
import id.ac.ui.cs.advprog.purchasepayment.models.CartDetails;
import id.ac.ui.cs.advprog.purchasepayment.ports.CartDetailsRepository;
import id.ac.ui.cs.advprog.purchasepayment.ports.CartRepository;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@UseCase
@RequiredArgsConstructor
public class UpdateCartImpl implements UpdateCart {
    private final CartRepository cartRepository;
    private final CartDetailsRepository cartDetailsRepository;
    private final GetCart getCart;

    @Override
    public Cart update(UpdateCartRequest request) {
        var userCart = getCart.getCartByUsername(request.getUsername());
        addCartDetailsToCartByRequest(request, userCart);
        return userCart;
    }

    @Override
    public CartDetails addCartDetailsToCartByRequest(UpdateCartRequest request, Cart cart) {
        var cartDetails = CartDetails.builder()
                .appId(request.getId())
                .appName(request.getName())
                .addDate(new Date())
                .appPrice(request.getPrice())
                .cart(cart)
                .build();
        return cartDetailsRepository.save(cartDetails);
    }
}
