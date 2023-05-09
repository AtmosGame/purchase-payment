package id.ac.ui.cs.advprog.purchasepayment.validation.updatecart.request;

import id.ac.ui.cs.advprog.purchasepayment.dto.UpdateCartRequest;
import id.ac.ui.cs.advprog.purchasepayment.exceptions.AppAlreadyInCheckoutException;
import id.ac.ui.cs.advprog.purchasepayment.validation.Validator;

public class AppNotInCheckoutValidator extends Validator<UpdateCartRequest> {
    @Override
    public boolean isValid(UpdateCartRequest request) {
        if (appNotInCheckout(request)) {
            nextValidatorIsValid(request);
        } else {
            throw new AppAlreadyInCheckoutException(request.getName(), request.getId());
        }

        return true;
    }

    public boolean appNotInCheckout(UpdateCartRequest request) {
        return true;
    }
}
