package id.ac.ui.cs.advprog.purchasepayment.web.processor.request;

import id.ac.ui.cs.advprog.purchasepayment.annotations.Processor;
import id.ac.ui.cs.advprog.purchasepayment.dto.UpdateCartRequest;
import id.ac.ui.cs.advprog.purchasepayment.validation.UpdateCartRequestValidator.UpdateCartRequestValidatorFactory;
import id.ac.ui.cs.advprog.purchasepayment.validation.UpdateCartRequestValidator.UpdateCartRequestValidatorFactoryImpl;
import id.ac.ui.cs.advprog.purchasepayment.validation.Validator;
import jakarta.annotation.PostConstruct;

@Processor
public class UpdateCartRequestProcessor implements RequestProcessor<UpdateCartRequest> {
    private Validator<UpdateCartRequest> validator;

    @PostConstruct
    private void init() {
        UpdateCartRequestValidatorFactory factory = new UpdateCartRequestValidatorFactoryImpl();
        Validator<UpdateCartRequest> updateCartRequestDataValidator = factory.createUpdateCartRequestDataValidator();
        Validator<UpdateCartRequest> appNotInListValidator = factory.createAppNotInListValidator();
        Validator<UpdateCartRequest> appNotInCartValidator = factory.createAppNotInCartValidator();
        Validator<UpdateCartRequest> appNotInCheckoutValidator = factory.createAppNotInCheckoutValidator();

        updateCartRequestDataValidator.setNextValidator(appNotInListValidator);
        appNotInListValidator.setNextValidator(appNotInCartValidator);
        appNotInCartValidator.setNextValidator(appNotInCheckoutValidator);

        validator = updateCartRequestDataValidator;
    }

    public void validate(UpdateCartRequest request) {
        validator.isValid(request);
    }
}
