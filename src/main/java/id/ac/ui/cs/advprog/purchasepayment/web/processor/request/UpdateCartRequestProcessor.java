package id.ac.ui.cs.advprog.purchasepayment.web.processor.request;

import id.ac.ui.cs.advprog.purchasepayment.annotations.Processor;
import id.ac.ui.cs.advprog.purchasepayment.dto.UpdateCartRequest;
import id.ac.ui.cs.advprog.purchasepayment.validation.updatecart.request.UpdateCartRequestValidatorFactory;
import id.ac.ui.cs.advprog.purchasepayment.validation.updatecart.request.UpdateCartRequestValidatorFactoryImpl;
import id.ac.ui.cs.advprog.purchasepayment.validation.Validator;
import jakarta.annotation.PostConstruct;
import lombok.Getter;

@Processor
@Getter
public class UpdateCartRequestProcessor implements RequestProcessor<UpdateCartRequest> {
    private Validator<UpdateCartRequest> validator;

    @PostConstruct
    public void init() {
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
