package id.ac.ui.cs.advprog.purchasepayment.web.processor.request;

import id.ac.ui.cs.advprog.purchasepayment.annotations.Processor;
import id.ac.ui.cs.advprog.purchasepayment.dto.UpdatePaymentRequest;
import id.ac.ui.cs.advprog.purchasepayment.validation.Validator;
import id.ac.ui.cs.advprog.purchasepayment.validation.updatepayment.request.UpdatePaymentRequestFactory;
import id.ac.ui.cs.advprog.purchasepayment.validation.updatepayment.request.UpdatePaymentRequestFactoryImpl;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

@Processor
@Getter
public class UpdatePaymentRequestProcessor implements RequestProcessor<UpdatePaymentRequest>{
    private Validator<UpdatePaymentRequest> validator;
    private Validator<UpdatePaymentRequest> secretTokenValidator;

    @Autowired
    public void setSecretTokenValidator(Validator<UpdatePaymentRequest> secretTokenValidator) {
        this.secretTokenValidator = secretTokenValidator;
    }

    @PostConstruct
    public void init() {
        UpdatePaymentRequestFactory factory = new UpdatePaymentRequestFactoryImpl();
        Validator<UpdatePaymentRequest> checkoutNotExpiredValidator = factory.createCheckoutNotExpiredValidator();

        secretTokenValidator.setNextValidator(checkoutNotExpiredValidator);

        validator = secretTokenValidator;
    }
    @Override
    public void validate(UpdatePaymentRequest request) {
        validator.isValid(request);
    }
}
