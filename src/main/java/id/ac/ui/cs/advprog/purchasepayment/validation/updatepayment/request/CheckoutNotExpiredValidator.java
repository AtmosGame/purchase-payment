package id.ac.ui.cs.advprog.purchasepayment.validation.updatepayment.request;

import id.ac.ui.cs.advprog.purchasepayment.dto.UpdatePaymentRequest;
import id.ac.ui.cs.advprog.purchasepayment.exceptions.CheckoutIsExpiredException;
import id.ac.ui.cs.advprog.purchasepayment.ports.CheckoutRepository;
import id.ac.ui.cs.advprog.purchasepayment.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CheckoutNotExpiredValidator extends Validator<UpdatePaymentRequest> {
    private CheckoutRepository checkoutRepository;
    @Override
    public boolean isValid(UpdatePaymentRequest request) {
        if (checkoutNotExpired(request)) {
            if (getNextValidator() != null) {
                getNextValidator().isValid(request);
            }
        } else {
            throw new CheckoutIsExpiredException(request.getId());
        }

        return true;
    }

    public boolean checkoutNotExpired(UpdatePaymentRequest request) {
        var checkout = checkoutRepository.findById(Integer.valueOf(request.getId())).get();
        return !checkout.getStatusPembayaran().equalsIgnoreCase("Expired");
    }

    @Autowired
    public void setCheckoutRepository(CheckoutRepository checkoutRepository) {
        this.checkoutRepository = checkoutRepository;
    }
}
