package id.ac.ui.cs.advprog.purchasepayment.validation.updatepayment.request;

import id.ac.ui.cs.advprog.purchasepayment.dto.UpdatePaymentRequest;
import id.ac.ui.cs.advprog.purchasepayment.exceptions.CheckoutIsExpiredException;
import id.ac.ui.cs.advprog.purchasepayment.validation.Validator;

public class CheckoutNotExpiredValidator extends Validator<UpdatePaymentRequest> {
    @Override
    public boolean isValid(UpdatePaymentRequest request) {
        if (checkoutNotExpired(request)) {
            if (getNextValidator() != null) {
                getNextValidator().isValid(request);
            }
        } else {
            throw new CheckoutIsExpiredException(request.getId());
        }

        return true;
    }

    public boolean checkoutNotExpired(UpdatePaymentRequest request) {
        return true;
    }
}