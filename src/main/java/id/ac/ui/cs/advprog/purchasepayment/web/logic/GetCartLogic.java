package id.ac.ui.cs.advprog.purchasepayment.web.logic;

import id.ac.ui.cs.advprog.purchasepayment.annotations.Logic;
import id.ac.ui.cs.advprog.purchasepayment.dto.GetCartResponse;
import id.ac.ui.cs.advprog.purchasepayment.usecase.GetCart;
import lombok.RequiredArgsConstructor;

@Logic
@RequiredArgsConstructor
public class GetCartLogic implements PurchaseAndPaymentLogic<String, GetCartResponse>{
    private final GetCart getCartImpl;

    @Override
    public GetCartResponse processLogic(String username) {
        return getCartImpl.findCartByUsername(username);
    }
}
