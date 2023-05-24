package id.ac.ui.cs.advprog.purchasepayment.validation.updatecart.request;

import id.ac.ui.cs.advprog.purchasepayment.dto.UpdateCartRequest;
import id.ac.ui.cs.advprog.purchasepayment.exceptions.AppAlreadyInCheckoutException;
import id.ac.ui.cs.advprog.purchasepayment.ports.CheckoutDetailsRepository;
import id.ac.ui.cs.advprog.purchasepayment.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AppNotInCheckoutValidator extends Validator<UpdateCartRequest> {
    CheckoutDetailsRepository checkoutDetailsRepository;
    @Override
    public boolean isValid(UpdateCartRequest request) {
        if (appNotInCheckout(request)) {
            nextValidatorIsValid(request);
        } else {
            throw new AppAlreadyInCheckoutException(request.getName(), request.getId());
        }

        return true;
    }

    public boolean appNotInCheckout(UpdateCartRequest request) {
        return !checkoutDetailsRepository.existsByCheckoutUsernameAndAppId(request.getUsername(), request.getId());
    }

    @Autowired
    public void setCheckoutDetailsRepository(CheckoutDetailsRepository checkoutDetailsRepository) {
        this.checkoutDetailsRepository = checkoutDetailsRepository;
    }
}
