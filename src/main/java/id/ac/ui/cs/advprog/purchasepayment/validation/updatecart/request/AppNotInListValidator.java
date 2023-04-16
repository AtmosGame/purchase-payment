package id.ac.ui.cs.advprog.purchasepayment.validation.updatecart.request;

import id.ac.ui.cs.advprog.purchasepayment.dto.UpdateCartRequest;
import id.ac.ui.cs.advprog.purchasepayment.validation.Validator;

public class AppNotInListValidator extends Validator<UpdateCartRequest> {
    @Override
    public boolean isValid(UpdateCartRequest request) {
        // implement method
        if (true) {
            if (getNextValidator() != null) {
                getNextValidator().isValid(request);
            }
        } else {
            // Throw error
        }

        return true;
    }
}
