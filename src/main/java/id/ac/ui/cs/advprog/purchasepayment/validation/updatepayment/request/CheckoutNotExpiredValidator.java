package id.ac.ui.cs.advprog.purchasepayment.validation.updatepayment.request;

import id.ac.ui.cs.advprog.purchasepayment.dto.UpdatePaymentRequest;
import id.ac.ui.cs.advprog.purchasepayment.validation.Validator;

public class CheckoutNotExpiredValidator extends Validator<UpdatePaymentRequest> {
    @Override
    public boolean isValid(UpdatePaymentRequest request) {
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
