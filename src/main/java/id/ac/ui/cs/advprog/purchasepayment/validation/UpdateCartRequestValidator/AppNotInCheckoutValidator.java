package id.ac.ui.cs.advprog.purchasepayment.validation.UpdateCartRequestValidator;

import id.ac.ui.cs.advprog.purchasepayment.dto.UpdateCartRequest;
import id.ac.ui.cs.advprog.purchasepayment.validation.Validator;

public class AppNotInCheckoutValidator extends Validator<UpdateCartRequest> {
    @Override
    public boolean isValid(UpdateCartRequest request) {
        // TODO: implement method
        if (true) {
            System.out.println("AppNotInCheckoutValidator: data valid");
            if (getNextValidator() != null) {
                getNextValidator().isValid(request);
            }
        } else {
            // TODO: Throw error
        }

        return true;
    }
}
