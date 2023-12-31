package id.ac.ui.cs.advprog.purchasepayment.web.processor.request;

import id.ac.ui.cs.advprog.purchasepayment.annotations.Processor;
import id.ac.ui.cs.advprog.purchasepayment.dto.UpdateCartRequest;
import id.ac.ui.cs.advprog.purchasepayment.validation.Validator;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

@Processor
@Getter
public class UpdateCartRequestProcessor implements RequestProcessor<UpdateCartRequest> {
    private Validator<UpdateCartRequest> validator;
    private Validator<UpdateCartRequest> appNotInCartValidator;
    private Validator<UpdateCartRequest> updateCartRequestDataValidator;
    private Validator<UpdateCartRequest> appNotInPurchasedAppValidator;
    private Validator<UpdateCartRequest> appNotInCheckoutValidator;


    @PostConstruct
    public void init() {
        updateCartRequestDataValidator.setNextValidator(appNotInPurchasedAppValidator);
        appNotInPurchasedAppValidator.setNextValidator(appNotInCartValidator);
        appNotInCartValidator.setNextValidator(appNotInCheckoutValidator);

        validator = updateCartRequestDataValidator;
    }

    public void validate(UpdateCartRequest request) {
        validator.isValid(request);
    }
    @Autowired
    public void setAppNotInCartValidator(Validator<UpdateCartRequest> appNotInCartValidator) {
        this.appNotInCartValidator = appNotInCartValidator;
    }

    @Autowired
    public void setUpdateCartRequestDataValidator(Validator<UpdateCartRequest> updateCartRequestDataValidator) {
        this.updateCartRequestDataValidator = updateCartRequestDataValidator;
    }

    @Autowired
    public void setAppNotInPurchasedAppValidator(Validator<UpdateCartRequest> appNotInPurchasedAppValidator) {
        this.appNotInPurchasedAppValidator = appNotInPurchasedAppValidator;
    }

    @Autowired
    public void setAppNotInCheckoutValidator(Validator<UpdateCartRequest> appNotInCheckoutValidator) {
        this.appNotInCheckoutValidator = appNotInCheckoutValidator;
    }
}
