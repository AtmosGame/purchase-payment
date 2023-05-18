package id.ac.ui.cs.advprog.purchasepayment.usecase;

import id.ac.ui.cs.advprog.purchasepayment.dto.GetCartResponse;
import id.ac.ui.cs.advprog.purchasepayment.models.Cart;

public interface GetCart {
    GetCartResponse findCartByUsername(String username);
    Cart getCartByUsername(String username);
}
