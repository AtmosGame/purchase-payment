package id.ac.ui.cs.advprog.purchasepayment.validation.checkoutcart.request;

import id.ac.ui.cs.advprog.purchasepayment.dto.CheckoutCartRequest;
import id.ac.ui.cs.advprog.purchasepayment.validation.Validator;

public class AppNotInCartValidator extends Validator<CheckoutCartRequest>{
    @Override
    public boolean isValid(CheckoutCartRequest request) {
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
