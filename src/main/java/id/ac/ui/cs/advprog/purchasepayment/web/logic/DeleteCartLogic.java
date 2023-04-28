package id.ac.ui.cs.advprog.purchasepayment.web.logic;

import id.ac.ui.cs.advprog.purchasepayment.annotations.Logic;
import id.ac.ui.cs.advprog.purchasepayment.usecase.DeleteCart;
import lombok.RequiredArgsConstructor;

@Logic
@RequiredArgsConstructor
public class DeleteCartLogic implements PurchaseAndPaymentLogic<String, Void> {
    private final DeleteCart deleteCartImpl;

    @Override
    public Void processLogic(String request) {
        // Get current user's username
        var username = "edutjie";

        return deleteCartImpl.deleteCartByAppId(username, request);
    }
}
