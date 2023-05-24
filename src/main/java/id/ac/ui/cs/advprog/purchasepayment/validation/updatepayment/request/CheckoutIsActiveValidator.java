package id.ac.ui.cs.advprog.purchasepayment.validation.updatepayment.request;

import id.ac.ui.cs.advprog.purchasepayment.dto.UpdatePaymentRequest;
import id.ac.ui.cs.advprog.purchasepayment.exceptions.CheckoutIsNotActiveException;
import id.ac.ui.cs.advprog.purchasepayment.ports.CheckoutRepository;
import id.ac.ui.cs.advprog.purchasepayment.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CheckoutIsActiveValidator extends Validator<UpdatePaymentRequest> {
    private CheckoutRepository checkoutRepository;

    @Override
    public boolean isValid(UpdatePaymentRequest request) {
        if (checkoutIsActive(request)) {
            if (getNextValidator() != null) {
                getNextValidator().isValid(request);
            }
        } else {
            throw new CheckoutIsNotActiveException(request.getId());
        }

        return true;
    }

    public boolean checkoutIsActive(UpdatePaymentRequest request) {
        var optionalCheckout = checkoutRepository.findById(Integer.valueOf(request.getId()));

        if (optionalCheckout.isEmpty()) {
            return false;
        }

        var checkout = optionalCheckout.get();
        return checkout.getStatusPembayaran().equalsIgnoreCase("Menunggu Pembayaran");
    }

    @Autowired
    public void setCheckoutRepository(CheckoutRepository checkoutRepository) {
        this.checkoutRepository = checkoutRepository;
    }
}
