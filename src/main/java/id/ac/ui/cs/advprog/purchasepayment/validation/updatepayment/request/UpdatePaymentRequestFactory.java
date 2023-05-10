package id.ac.ui.cs.advprog.purchasepayment.validation.updatepayment.request;

import id.ac.ui.cs.advprog.purchasepayment.dto.UpdatePaymentRequest;
import id.ac.ui.cs.advprog.purchasepayment.validation.Validator;

public interface UpdatePaymentRequestFactory {
    Validator<UpdatePaymentRequest> createSecretTokenValidator();
    Validator<UpdatePaymentRequest> createCheckoutNotExpiredValidator();
}
