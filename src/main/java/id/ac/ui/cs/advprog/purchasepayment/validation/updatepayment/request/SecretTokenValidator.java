package id.ac.ui.cs.advprog.purchasepayment.validation.updatepayment.request;

import id.ac.ui.cs.advprog.purchasepayment.dto.UpdatePaymentRequest;
import id.ac.ui.cs.advprog.purchasepayment.exceptions.SecretTokenInvalidException;
import id.ac.ui.cs.advprog.purchasepayment.validation.Validator;

public class SecretTokenValidator extends Validator<UpdatePaymentRequest> {
    @Override
    public boolean isValid(UpdatePaymentRequest request) {
        if (secretTokenValid(request)) {
            if (getNextValidator() != null) {
                getNextValidator().isValid(request);
            }
        } else {
            throw new SecretTokenInvalidException(request.getToken());
        }

        return true;
    }

    public boolean secretTokenValid(UpdatePaymentRequest request) {
        return true;
    }
}
