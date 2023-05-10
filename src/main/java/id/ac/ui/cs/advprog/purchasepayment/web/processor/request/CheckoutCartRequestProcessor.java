package id.ac.ui.cs.advprog.purchasepayment.web.processor.request;

import id.ac.ui.cs.advprog.purchasepayment.annotations.Processor;
import id.ac.ui.cs.advprog.purchasepayment.dto.CheckoutCartRequest;
import id.ac.ui.cs.advprog.purchasepayment.validation.Validator;
import id.ac.ui.cs.advprog.purchasepayment.validation.checkoutcart.request.CheckoutCartRequestValidatorFactory;
import id.ac.ui.cs.advprog.purchasepayment.validation.checkoutcart.request.CheckoutCartRequestValidatorFactoryImpl;
import jakarta.annotation.PostConstruct;
import lombok.Getter;

@Processor
@Getter
public class CheckoutCartRequestProcessor implements RequestProcessor<CheckoutCartRequest> {
    private Validator<CheckoutCartRequest> validator;

    @PostConstruct
    public void init() {
        CheckoutCartRequestValidatorFactory factory = new CheckoutCartRequestValidatorFactoryImpl();
        Validator<CheckoutCartRequest> checkoutCartRequestDataValidator = factory.createCheckoutCartRequestDataValidator();
        Validator<CheckoutCartRequest> appNotInListValidator = factory.createAppNotInListValidator();
        Validator<CheckoutCartRequest> appNotInCartValidator = factory.createAppNotInCartValidator();
        Validator<CheckoutCartRequest> appNotInCheckoutValidator = factory.createAppNotInCheckoutValidator();

        checkoutCartRequestDataValidator.setNextValidator(appNotInListValidator);
        appNotInListValidator.setNextValidator(appNotInCartValidator);
        appNotInCartValidator.setNextValidator(appNotInCheckoutValidator);

        validator = checkoutCartRequestDataValidator;
    }

    public void validate(CheckoutCartRequest request) {
        validator.isValid(request);
    }
}
