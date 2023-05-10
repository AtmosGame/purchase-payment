package id.ac.ui.cs.advprog.purchasepayment.usecase;

import id.ac.ui.cs.advprog.purchasepayment.annotations.UseCase;
import id.ac.ui.cs.advprog.purchasepayment.dto.GetCartResponse;
import id.ac.ui.cs.advprog.purchasepayment.exceptions.CartDoesNotExistException;
import id.ac.ui.cs.advprog.purchasepayment.models.CartDetails;
import id.ac.ui.cs.advprog.purchasepayment.ports.CartDetailsRepository;
import id.ac.ui.cs.advprog.purchasepayment.ports.CartRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class GetCartImpl implements GetCart {
    private final CartRepository cartRepository;
    private final CartDetailsRepository cartDetailsRepository;

    @Override
    public GetCartResponse findCartByUsername(String username) {
        // get cart
        var cart = cartRepository.findByUsername(username).orElse(null);

        // validate
        if (cart == null) {
            throw new CartDoesNotExistException(username);
        }

        // get cartDetails by cartId
        List<CartDetails> cartDetails = cartDetailsRepository.findAllByCartId(cart.getId());
        return GetCartResponse.fromCart(cart, cartDetails);
    }
}
