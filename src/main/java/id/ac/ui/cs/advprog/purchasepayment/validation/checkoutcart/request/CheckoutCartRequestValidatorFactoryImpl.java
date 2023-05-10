package id.ac.ui.cs.advprog.purchasepayment.validation.checkoutcart.request;

import id.ac.ui.cs.advprog.purchasepayment.dto.CheckoutCartRequest;
import id.ac.ui.cs.advprog.purchasepayment.validation.Validator;

public class CheckoutCartRequestValidatorFactoryImpl implements CheckoutCartRequestValidatorFactory{
    @Override
    public Validator<CheckoutCartRequest> createCheckoutCartRequestDataValidator() {
        return new CheckoutCartRequestDataValidator();
    }

    @Override
    public Validator<CheckoutCartRequest> createAppNotInListValidator() {
        return new AppNotInListValidator();
    }

    @Override
    public Validator<CheckoutCartRequest> createAppNotInCartValidator() {
        return new AppNotInCartValidator();
    }

    @Override
    public Validator<CheckoutCartRequest> createAppNotInCheckoutValidator() {
        return new AppNotInCheckoutValidator();
    }
}
