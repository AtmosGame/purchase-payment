package id.ac.ui.cs.advprog.purchasepayment.validation.updatecartrequestvalidator;

import id.ac.ui.cs.advprog.purchasepayment.dto.UpdateCartRequest;
import id.ac.ui.cs.advprog.purchasepayment.validation.Validator;

public class AppNotInListValidator extends Validator<UpdateCartRequest> {
    @Override
    public boolean isValid(UpdateCartRequest request) {
        // TODO: implement method
        if (true) {
            if (getNextValidator() != null) {
                getNextValidator().isValid(request);
            }
        } else {
            // TODO: Throw error
        }

        return true;
    }
}
