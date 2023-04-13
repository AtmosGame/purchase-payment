package id.ac.ui.cs.advprog.purchasepayment.validation.CheckPurchasedApps.response;

import id.ac.ui.cs.advprog.purchasepayment.dto.CheckPurchasedResponse;
import id.ac.ui.cs.advprog.purchasepayment.validation.Validator;

public class CheckPurchasedResponseValidator extends Validator<CheckPurchasedResponse> {
    @Override
    public boolean isValid(CheckPurchasedResponse request) {
        Boolean isPurchased = request.getIsPurchased();

        if (isPurchased == null) {
            // TODO: throw error
        }
        return true;
    }
}
