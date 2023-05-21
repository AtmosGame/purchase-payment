package id.ac.ui.cs.advprog.purchasepayment.validation.updatepayment.request;

import id.ac.ui.cs.advprog.purchasepayment.dto.UpdatePaymentRequest;
import id.ac.ui.cs.advprog.purchasepayment.exceptions.CheckoutIsExpiredException;
import id.ac.ui.cs.advprog.purchasepayment.exceptions.RequestDataInvalidException;
import id.ac.ui.cs.advprog.purchasepayment.ports.CheckoutRepository;
import id.ac.ui.cs.advprog.purchasepayment.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdatePaymentRequestDataValidator extends Validator<UpdatePaymentRequest> {

    private CheckoutRepository checkoutRepository;

    @Override
    public boolean isValid(UpdatePaymentRequest request) {
        if (checkoutDataIsValid(request)) {
            if (getNextValidator() != null) {
                getNextValidator().isValid(request);
            }
        } else {
            throw new RequestDataInvalidException();
        }

        return true;
    }

    public boolean checkoutDataIsValid(UpdatePaymentRequest request) {
        try {
            var result = checkoutRepository.existsById(Integer.valueOf(request.getId()));
            return result;
        } catch (Exception e) {
            return false;
        }
    }

    @Autowired
    public void setCheckoutRepository(CheckoutRepository checkoutRepository) {
        this.checkoutRepository = checkoutRepository;
    }
}
