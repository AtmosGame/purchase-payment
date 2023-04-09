package id.ac.ui.cs.advprog.purchasepayment.validation.updatecartrequestvalidator;

import id.ac.ui.cs.advprog.purchasepayment.dto.UpdateCartRequest;
import id.ac.ui.cs.advprog.purchasepayment.validation.Validator;

public class UpdateCartRequestValidatorFactoryImpl implements  UpdateCartRequestValidatorFactory{
    @Override
    public Validator<UpdateCartRequest> createUpdateCartRequestDataValidator() {
        return new UpdateCartRequestDataValidator();
    }

    @Override
    public Validator<UpdateCartRequest> createAppNotInListValidator() {
        return new AppNotInListValidator();
    }

    @Override
    public Validator<UpdateCartRequest> createAppNotInCartValidator() {
        return new AppNotInCartValidator();
    }

    @Override
    public Validator<UpdateCartRequest> createAppNotInCheckoutValidator() {
        return new AppNotInCheckoutValidator();
    }
}
