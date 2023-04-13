package id.ac.ui.cs.advprog.purchasepayment.validation.CheckPurchasedApps.request;

import id.ac.ui.cs.advprog.purchasepayment.dto.CheckPurchasedRequest;
import id.ac.ui.cs.advprog.purchasepayment.validation.Validator;

public class CheckPurchasedRequestValidator extends Validator<CheckPurchasedRequest> {
    @Override
    public boolean isValid(CheckPurchasedRequest request) {
        String userId = request.getUserId();
        String appId = request.getAppId();

        if (appId.equals("")) {
            // TODO: throw error no app id
        } else if (userId.equals("")) {
            // TODO: Throw error
        }
        return true;
    }
}
