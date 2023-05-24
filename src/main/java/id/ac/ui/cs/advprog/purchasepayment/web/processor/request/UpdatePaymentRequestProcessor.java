package id.ac.ui.cs.advprog.purchasepayment.web.processor.request;

import id.ac.ui.cs.advprog.purchasepayment.annotations.Processor;
import id.ac.ui.cs.advprog.purchasepayment.dto.UpdatePaymentRequest;
import id.ac.ui.cs.advprog.purchasepayment.validation.Validator;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

@Processor
@Getter
public class UpdatePaymentRequestProcessor implements RequestProcessor<UpdatePaymentRequest>{
    private Validator<UpdatePaymentRequest> validator;
    private Validator<UpdatePaymentRequest> secretTokenValidator;
    private Validator<UpdatePaymentRequest> checkoutIsActiveValidator;
    private Validator<UpdatePaymentRequest> updatePaymentRequestDataValidator;

    @PostConstruct
    public void init() {
        secretTokenValidator.setNextValidator(updatePaymentRequestDataValidator);
        updatePaymentRequestDataValidator.setNextValidator(checkoutIsActiveValidator);

        validator = secretTokenValidator;
    }
    @Override
    public void validate(UpdatePaymentRequest request) {
        validator.isValid(request);
    }

    @Autowired
    public void setSecretTokenValidator(Validator<UpdatePaymentRequest> secretTokenValidator) {
        this.secretTokenValidator = secretTokenValidator;
    }

    @Autowired
    public void setUpdatePaymentRequestDataValidator(Validator<UpdatePaymentRequest> updatePaymentRequestDataValidator) {
        this.updatePaymentRequestDataValidator = updatePaymentRequestDataValidator;
    }

    @Autowired
    public void setCheckoutIsActiveValidator(Validator<UpdatePaymentRequest> checkoutIsActiveValidator) {
        this.checkoutIsActiveValidator = checkoutIsActiveValidator;
    }
}
