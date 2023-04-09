package id.ac.ui.cs.advprog.purchasepayment.validation.updatecartrequestvalidator;

import id.ac.ui.cs.advprog.purchasepayment.dto.UpdateCartRequest;
import id.ac.ui.cs.advprog.purchasepayment.validation.Validator;

public interface UpdateCartRequestValidatorFactory {
    Validator<UpdateCartRequest> createUpdateCartRequestDataValidator();
    Validator<UpdateCartRequest> createAppNotInListValidator();
    Validator<UpdateCartRequest> createAppNotInCartValidator();
    Validator<UpdateCartRequest> createAppNotInCheckoutValidator();
}
