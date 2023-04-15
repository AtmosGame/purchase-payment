package id.ac.ui.cs.advprog.purchasepayment.usecase;

import id.ac.ui.cs.advprog.purchasepayment.dto.GetCartResponse;
import id.ac.ui.cs.advprog.purchasepayment.models.Cart;
import id.ac.ui.cs.advprog.purchasepayment.models.CartDetails;

import java.util.List;
import java.util.Optional;

public interface GetCart {
    GetCartResponse findCartByUsername(String username);
}
