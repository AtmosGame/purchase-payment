package id.ac.ui.cs.advprog.purchasepayment.web.logic;

import id.ac.ui.cs.advprog.purchasepayment.annotations.Logic;
import id.ac.ui.cs.advprog.purchasepayment.dto.DeleteCartRequest;
import id.ac.ui.cs.advprog.purchasepayment.usecase.DeleteCart;
import lombok.RequiredArgsConstructor;

@Logic
@RequiredArgsConstructor
public class DeleteCartLogic implements PurchaseAndPaymentLogic<DeleteCartRequest, Void> {
    private final DeleteCart deleteCartImpl;

    @Override
    public Void processLogic(DeleteCartRequest request) {
        return deleteCartImpl.deleteCartByAppId(request.getUsername(), request.getAppId());
    }
}
