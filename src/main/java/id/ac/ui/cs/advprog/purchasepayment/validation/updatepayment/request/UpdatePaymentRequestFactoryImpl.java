package id.ac.ui.cs.advprog.purchasepayment.validation.updatepayment.request;

import id.ac.ui.cs.advprog.purchasepayment.dto.UpdatePaymentRequest;
import id.ac.ui.cs.advprog.purchasepayment.validation.Validator;

public class UpdatePaymentRequestFactoryImpl implements UpdatePaymentRequestFactory{
    @Override
    public Validator<UpdatePaymentRequest> createSecretTokenValidator() {
        return new SecretTokenValidator();
    }

    @Override
    public Validator<UpdatePaymentRequest> createCheckoutNotExpiredValidator() {
        return new CheckoutNotExpiredValidator();
    }
}