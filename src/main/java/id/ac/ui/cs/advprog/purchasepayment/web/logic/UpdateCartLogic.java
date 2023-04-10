package id.ac.ui.cs.advprog.purchasepayment.web.logic;

import id.ac.ui.cs.advprog.purchasepayment.annotations.Logic;
import id.ac.ui.cs.advprog.purchasepayment.dto.UpdateCartRequest;
import id.ac.ui.cs.advprog.purchasepayment.usecase.UpdateCart;
import id.ac.ui.cs.advprog.purchasepayment.web.processor.request.RequestProcessor;
import lombok.RequiredArgsConstructor;

@Logic
@RequiredArgsConstructor
public class UpdateCartLogic implements PurchaseAndPaymentLogic<UpdateCartRequest, Void>{
    private final RequestProcessor<UpdateCartRequest> updateCartRequestProcessor;
    private final UpdateCart updateCartImpl;

    @Override
    public Void processLogic(UpdateCartRequest request) {
        updateCartRequestProcessor.validate(request);
        updateCartImpl.update(request);
        return null;
    }
}
