package id.ac.ui.cs.advprog.purchasepayment.usecase;

import id.ac.ui.cs.advprog.purchasepayment.annotations.UseCase;
import id.ac.ui.cs.advprog.purchasepayment.dto.CheckoutCartRequest;
import id.ac.ui.cs.advprog.purchasepayment.exceptions.AppAlreadyInCartException;
import id.ac.ui.cs.advprog.purchasepayment.exceptions.CartIsEmptyException;
import id.ac.ui.cs.advprog.purchasepayment.models.Cart;
import id.ac.ui.cs.advprog.purchasepayment.models.CartDetails;
import id.ac.ui.cs.advprog.purchasepayment.models.Checkout;
import id.ac.ui.cs.advprog.purchasepayment.ports.CartDetailsRepository;
import id.ac.ui.cs.advprog.purchasepayment.ports.CartRepository;
import id.ac.ui.cs.advprog.purchasepayment.ports.CheckoutRepository;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.Optional;

@UseCase
@RequiredArgsConstructor
public class CheckoutCartImpl implements CheckoutCart {
    private final CartRepository cartRepository;
    private final CartDetailsRepository cartDetailsRepository;
    private final CheckoutRepository checkoutRepository;

    @Override
    public Checkout checkout(CheckoutCartRequest request) {
        if (checkCartIsEmpty(request.getUsername())){
            throw new CartIsEmptyException(request.getUsername());
        }
        var userCheckout = Checkout.builder().statusPembayaran("Menunggu Pembayaran").build();
        return checkoutRepository.save(userCheckout);
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

    public boolean checkCartIsEmpty(String username) {
        Cart getCart = getCartByUsername(username);
        // check if the cart is empty
        if (getCart.getCartDetails().isEmpty()) {
            return true;
        }
        return false;
    }
}
