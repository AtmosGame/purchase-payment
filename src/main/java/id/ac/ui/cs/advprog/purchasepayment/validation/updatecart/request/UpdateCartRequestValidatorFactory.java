package id.ac.ui.cs.advprog.purchasepayment.validation.updatecart.request;

import id.ac.ui.cs.advprog.purchasepayment.dto.UpdateCartRequest;
import id.ac.ui.cs.advprog.purchasepayment.validation.Validator;

public interface UpdateCartRequestValidatorFactory {
    Validator<UpdateCartRequest> createUpdateCartRequestDataValidator();
    Validator<UpdateCartRequest> createAppNotInPurchasedAppValidator();
    Validator<UpdateCartRequest> createAppNotInCartValidator();
    Validator<UpdateCartRequest> createAppNotInCheckoutValidator();
}
