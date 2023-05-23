package id.ac.ui.cs.advprog.purchasepayment.web.logic;

import id.ac.ui.cs.advprog.purchasepayment.annotations.Logic;
import id.ac.ui.cs.advprog.purchasepayment.dto.CheckPurchasedRequest;
import id.ac.ui.cs.advprog.purchasepayment.dto.CheckPurchasedResponse;
import id.ac.ui.cs.advprog.purchasepayment.usecase.CheckPurchasedApp;
import id.ac.ui.cs.advprog.purchasepayment.web.processor.request.RequestProcessor;
import id.ac.ui.cs.advprog.purchasepayment.web.processor.response.ResponseProcessor;
import lombok.RequiredArgsConstructor;

@Logic
@RequiredArgsConstructor
public class CheckPurchasedLogic implements PurchaseAndPaymentLogic<CheckPurchasedRequest, Boolean>{
    private final RequestProcessor<CheckPurchasedRequest> checkPurchasedRequestProcessor;
    private final ResponseProcessor<CheckPurchasedResponse, Boolean> checkPurchasedResponseProcessor;
    private final CheckPurchasedApp checkPurchasedAppImpl;
    private Boolean isPurchased;


    @Override
    public Boolean processLogic(CheckPurchasedRequest request) {
        checkPurchasedRequestProcessor.validate(request);
        this.isPurchased = checkPurchasedAppImpl.isPurchased(request);
        CheckPurchasedResponse response = new CheckPurchasedResponse(isPurchased);
        checkPurchasedResponseProcessor.process(response);
        return isPurchased;
    }
}
