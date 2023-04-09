package id.ac.ui.cs.advprog.purchasepayment.web.logic;

import id.ac.ui.cs.advprog.purchasepayment.annotations.Logic;
import id.ac.ui.cs.advprog.purchasepayment.dto.UpdateCartRequest;
import id.ac.ui.cs.advprog.purchasepayment.usecase.UpdateCart;
import id.ac.ui.cs.advprog.purchasepayment.web.processor.request.RequestProcessor;
import lombok.RequiredArgsConstructor;

@Logic
@RequiredArgsConstructor
public class UpdateCartLogic implements PurchaseAndPaymentLogic<UpdateCartRequest>{
    private final RequestProcessor<UpdateCartRequest> updateCartRequestProcessor;
    private final UpdateCart updateCartimpl;

    @Override
    public void processLogic(UpdateCartRequest request) {
        updateCartRequestProcessor.validate(request);
        updateCartimpl.update(request);
    }
}
