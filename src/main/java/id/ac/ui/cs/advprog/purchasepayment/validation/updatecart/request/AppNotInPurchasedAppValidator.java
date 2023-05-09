package id.ac.ui.cs.advprog.purchasepayment.validation.updatecart.request;

import id.ac.ui.cs.advprog.purchasepayment.dto.UpdateCartRequest;
import id.ac.ui.cs.advprog.purchasepayment.exceptions.AppAlreadyInPurchasedAppException;
import id.ac.ui.cs.advprog.purchasepayment.validation.Validator;

public class AppNotInPurchasedAppValidator extends Validator<UpdateCartRequest> {
    @Override
    public boolean isValid(UpdateCartRequest request) {
        if (appNotInPurchasedApp(request)) {
            nextValidatorIsValid(request);
        } else {
            throw new AppAlreadyInPurchasedAppException(request.getName(), request.getId());
        }

        return true;
    }

    public boolean appNotInPurchasedApp(UpdateCartRequest request) {
        return true;
    }
}
