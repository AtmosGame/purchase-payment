package id.ac.ui.cs.advprog.purchasepayment.web.logic;

import id.ac.ui.cs.advprog.purchasepayment.annotations.Logic;
import id.ac.ui.cs.advprog.purchasepayment.dto.CheckoutCartRequest;
import id.ac.ui.cs.advprog.purchasepayment.usecase.CheckoutCart;
import id.ac.ui.cs.advprog.purchasepayment.web.processor.request.RequestProcessor;
import lombok.RequiredArgsConstructor;

@Logic
@RequiredArgsConstructor
public class CheckoutCartLogic implements PurchaseAndPaymentLogic<CheckoutCartRequest, Void>{
    private final RequestProcessor<CheckoutCartRequest> checkoutCartRequestProcessor;
    private final CheckoutCart checkoutCartImpl;

    @Override
    public Void processLogic(CheckoutCartRequest request) {
        checkoutCartRequestProcessor.validate(request);
        checkoutCartImpl.checkout(request);
        return null;
    }
}

