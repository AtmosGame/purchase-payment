package id.ac.ui.cs.advprog.purchasepayment.web.logic;

import id.ac.ui.cs.advprog.purchasepayment.annotations.Logic;
import id.ac.ui.cs.advprog.purchasepayment.dto.CheckPurchasedRequest;
import id.ac.ui.cs.advprog.purchasepayment.dto.CheckPurchasedResponse;
import id.ac.ui.cs.advprog.purchasepayment.usecase.CheckPurchased.CheckPurchasedApp;
import id.ac.ui.cs.advprog.purchasepayment.web.processor.request.RequestProcessor;
import id.ac.ui.cs.advprog.purchasepayment.web.processor.response.ResponseProcessor;
import lombok.RequiredArgsConstructor;

@Logic
@RequiredArgsConstructor
public class CheckPurchasedLogic implements PurchaseAndPaymentLogic<CheckPurchasedRequest, Void>{
    private final RequestProcessor<CheckPurchasedRequest> CheckPurchasedRequestProcessor;
    private final ResponseProcessor<CheckPurchasedResponse, Boolean> CheckPurchasedResponseProcessor;
    private final CheckPurchasedApp checkPurchasedAppImpl;
    public Boolean isPurchased;


    @Override
    public Void processLogic(CheckPurchasedRequest request) {
        CheckPurchasedRequestProcessor.validate(request);
        boolean isPurchased = checkPurchasedAppImpl.isPurchased(request);
        CheckPurchasedResponse response = new CheckPurchasedResponse(isPurchased);
        this.isPurchased = CheckPurchasedResponseProcessor.process(response);
        return null;
    }
    public Boolean getIsPurchased() {
        return isPurchased;
    }
}
