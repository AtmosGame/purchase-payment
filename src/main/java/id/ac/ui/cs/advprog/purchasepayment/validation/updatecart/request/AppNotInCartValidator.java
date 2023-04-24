package id.ac.ui.cs.advprog.purchasepayment.validation.updatecart.request;

import id.ac.ui.cs.advprog.purchasepayment.dto.UpdateCartRequest;
import id.ac.ui.cs.advprog.purchasepayment.exceptions.AppAlreadyInCartException;
import id.ac.ui.cs.advprog.purchasepayment.validation.Validator;

public class AppNotInCartValidator extends Validator<UpdateCartRequest> {
    @Override
    public boolean isValid(UpdateCartRequest request) {
        if (appNotInCart(request)) {
            if (getNextValidator() != null) {
                getNextValidator().isValid(request);
            }
        } else {
            throw new AppAlreadyInCartException(request.getName(), request.getId());
        }

        return true;
    }

    public boolean appNotInCart(UpdateCartRequest request) {
        return true;
    }
}
