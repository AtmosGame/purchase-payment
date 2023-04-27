package id.ac.ui.cs.advprog.purchasepayment.validation.updatepayment.request;

import id.ac.ui.cs.advprog.purchasepayment.dto.UpdatePaymentRequest;
import id.ac.ui.cs.advprog.purchasepayment.exceptions.SecretTokenInvalidException;
import id.ac.ui.cs.advprog.purchasepayment.ports.SecretTokenRepository;
import id.ac.ui.cs.advprog.purchasepayment.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SecretTokenValidator extends Validator<UpdatePaymentRequest> {
    private SecretTokenRepository secretTokenRepository;

    @Autowired
    public void setSecretTokenRepository(SecretTokenRepository secretTokenRepository) {
        this.secretTokenRepository = secretTokenRepository;
    }

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
        return secretTokenRepository.existsByTokenName(request.getToken());
    }
}
