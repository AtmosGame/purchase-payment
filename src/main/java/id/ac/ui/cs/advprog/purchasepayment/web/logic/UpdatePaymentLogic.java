package id.ac.ui.cs.advprog.purchasepayment.web.logic;

import id.ac.ui.cs.advprog.purchasepayment.annotations.Logic;
import id.ac.ui.cs.advprog.purchasepayment.dto.UpdatePaymentRequest;
import id.ac.ui.cs.advprog.purchasepayment.usecase.UpdatePayment;
import id.ac.ui.cs.advprog.purchasepayment.web.processor.request.RequestProcessor;
import lombok.RequiredArgsConstructor;

@Logic
@RequiredArgsConstructor
public class UpdatePaymentLogic implements PurchaseAndPaymentLogic<UpdatePaymentRequest, Void> {
    private final RequestProcessor<UpdatePaymentRequest> updatePaymentProcessor;
    private final UpdatePayment updatePaymentImpl;

    @Override
    public Void processLogic(UpdatePaymentRequest request) {
        updatePaymentProcessor.validate(request);
        updatePaymentImpl.update(request);
        return null;
    }
}
