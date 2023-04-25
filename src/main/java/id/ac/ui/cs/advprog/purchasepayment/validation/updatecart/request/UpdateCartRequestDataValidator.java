package id.ac.ui.cs.advprog.purchasepayment.validation.updatecart.request;

import id.ac.ui.cs.advprog.purchasepayment.dto.UpdateCartRequest;
import id.ac.ui.cs.advprog.purchasepayment.exceptions.RequestDataInvalidException;
import id.ac.ui.cs.advprog.purchasepayment.validation.Validator;

public class UpdateCartRequestDataValidator extends Validator<UpdateCartRequest> {
    @Override
    public boolean isValid(UpdateCartRequest request) {
        if (requestDataValid(request)) {
            if (getNextValidator() != null) {
                getNextValidator().isValid(request);
            }
        } else {
            throw new RequestDataInvalidException();
        }

        return true;
    }

    public boolean requestDataValid(UpdateCartRequest request) {
        return true;
    }
}
