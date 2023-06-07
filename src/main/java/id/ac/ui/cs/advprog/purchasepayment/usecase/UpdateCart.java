package id.ac.ui.cs.advprog.purchasepayment.usecase;

import id.ac.ui.cs.advprog.purchasepayment.dto.UpdateCartRequest;
import id.ac.ui.cs.advprog.purchasepayment.models.Cart;
import id.ac.ui.cs.advprog.purchasepayment.models.CartDetails;

import java.util.Optional;

public interface UpdateCart {
    Cart update(UpdateCartRequest request);
    CartDetails addCartDetailsToCartByRequest(UpdateCartRequest request, Cart cart);
}
