package id.ac.ui.cs.advprog.purchasepayment.web.logic;

import id.ac.ui.cs.advprog.purchasepayment.annotations.Logic;
import id.ac.ui.cs.advprog.purchasepayment.dto.AddSecretTokenRequest;
import id.ac.ui.cs.advprog.purchasepayment.usecase.AddSecretToken;
import id.ac.ui.cs.advprog.purchasepayment.web.processor.request.RequestProcessor;
import lombok.RequiredArgsConstructor;

@Logic
@RequiredArgsConstructor
public class AddSecretTokenLogic implements PurchaseAndPaymentLogic<AddSecretTokenRequest, Void>{
    private final RequestProcessor<AddSecretTokenRequest> addSecretTokenRequestRequestProcessor;
    private final AddSecretToken addSecretTokenImpl;


    @Override
    public Void processLogic(AddSecretTokenRequest request) {
        addSecretTokenRequestRequestProcessor.validate(request);
        addSecretTokenImpl.addSecretTokenByRequest(request);
        return null;
    }
}
