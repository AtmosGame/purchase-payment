package id.ac.ui.cs.advprog.purchasepayment.validation.checkpurchasedapps.request;

import id.ac.ui.cs.advprog.purchasepayment.dto.CheckPurchasedRequest;
import id.ac.ui.cs.advprog.purchasepayment.exceptions.AppIsNotAvailableException;
import id.ac.ui.cs.advprog.purchasepayment.exceptions.UsernameIsNotAvailableException;
import id.ac.ui.cs.advprog.purchasepayment.validation.Validator;

public class CheckPurchasedRequestValidator extends Validator<CheckPurchasedRequest> {
    @Override
    public boolean isValid(CheckPurchasedRequest request) {
        String username = request.getUsername();
        String appId = request.getAppId();

        if (appId.equals("")) {
            throw new AppIsNotAvailableException(appId);
        } else if (username.equals("")) {
            throw new UsernameIsNotAvailableException(username);
        }
        return true;
    }
}
