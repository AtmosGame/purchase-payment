package id.ac.ui.cs.advprog.purchasepayment.validation.checkoutcart.request;

import id.ac.ui.cs.advprog.purchasepayment.dto.CheckoutCartRequest;
import id.ac.ui.cs.advprog.purchasepayment.validation.Validator;

public interface CheckoutCartRequestValidatorFactory {
    Validator<CheckoutCartRequest> createCheckoutCartRequestDataValidator();
    Validator<CheckoutCartRequest> createAppNotInListValidator();
    Validator<CheckoutCartRequest> createAppNotInCartValidator();
    Validator<CheckoutCartRequest> createAppNotInCheckoutValidator();
}
