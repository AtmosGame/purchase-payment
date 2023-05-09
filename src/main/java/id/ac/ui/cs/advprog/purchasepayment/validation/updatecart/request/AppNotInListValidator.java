package id.ac.ui.cs.advprog.purchasepayment.validation.updatecart.request;

import id.ac.ui.cs.advprog.purchasepayment.dto.UpdateCartRequest;
import id.ac.ui.cs.advprog.purchasepayment.exceptions.AppAlreadyInListException;
import id.ac.ui.cs.advprog.purchasepayment.validation.Validator;

public class AppNotInListValidator extends Validator<UpdateCartRequest> {
    @Override
    public boolean isValid(UpdateCartRequest request) {
        if (appNotInList(request)) {
            nextValidatorIsValid(request);
        } else {
            throw new AppAlreadyInListException(request.getName(), request.getId());
        }

        return true;
    }

    public boolean appNotInList(UpdateCartRequest request) {
        return true;
    }
}
