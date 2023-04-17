package id.ac.ui.cs.advprog.purchasepayment.web.logic;

import id.ac.ui.cs.advprog.purchasepayment.annotations.Logic;
import id.ac.ui.cs.advprog.purchasepayment.dto.GetCartResponse;
import id.ac.ui.cs.advprog.purchasepayment.usecase.GetCart;
import id.ac.ui.cs.advprog.purchasepayment.web.processor.request.RequestProcessor;
import lombok.RequiredArgsConstructor;

@Logic
@RequiredArgsConstructor
public class GetCartLogic implements PurchaseAndPaymentLogic<Void, GetCartResponse>{
    private final RequestProcessor<Void> getCartRequestProcessor;
    private final GetCart getCartImpl;

    @Override
    public GetCartResponse processLogic(Void request) {
        // TODO: Get current user's username
        var username = "edutjie";

        getCartRequestProcessor.validate(request);
        return getCartImpl.findCartByUsername(username);
    }
}
