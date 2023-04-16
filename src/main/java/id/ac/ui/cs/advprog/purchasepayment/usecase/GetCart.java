package id.ac.ui.cs.advprog.purchasepayment.usecase;

import id.ac.ui.cs.advprog.purchasepayment.dto.GetCartResponse;

public interface GetCart {
    GetCartResponse findCartByUsername(String username);
}
