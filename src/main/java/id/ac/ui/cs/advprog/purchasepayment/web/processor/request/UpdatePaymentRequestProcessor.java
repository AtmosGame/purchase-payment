package id.ac.ui.cs.advprog.purchasepayment.web.processor.request;

import id.ac.ui.cs.advprog.purchasepayment.annotations.Processor;
import id.ac.ui.cs.advprog.purchasepayment.dto.UpdatePaymentRequest;
import id.ac.ui.cs.advprog.purchasepayment.validation.Validator;
import id.ac.ui.cs.advprog.purchasepayment.validation.updatepayment.request.UpdatePaymentRequestFactory;
import id.ac.ui.cs.advprog.purchasepayment.validation.updatepayment.request.UpdatePaymentRequestFactoryImpl;
import jakarta.annotation.PostConstruct;
import lombok.Getter;

@Processor
@Getter
public class UpdatePaymentRequestProcessor implements RequestProcessor<UpdatePaymentRequest>{
    private Validator<UpdatePaymentRequest> validator;

    @PostConstruct
    public void init() {
        UpdatePaymentRequestFactory factory = new UpdatePaymentRequestFactoryImpl();
        Validator<UpdatePaymentRequest> secretTokenValidator = factory.createSecretTokenValidator();
        Validator<UpdatePaymentRequest> checkoutNotExpiredValidator = factory.createCheckoutNotExpiredValidator();

        secretTokenValidator.setNextValidator(checkoutNotExpiredValidator);

        validator = secretTokenValidator;
    }
    @Override
    public void validate(UpdatePaymentRequest request) {
        validator.isValid(request);
    }
}
