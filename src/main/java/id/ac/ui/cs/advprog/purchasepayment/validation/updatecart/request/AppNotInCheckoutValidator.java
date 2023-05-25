package id.ac.ui.cs.advprog.purchasepayment.validation.updatecart.request;

import id.ac.ui.cs.advprog.purchasepayment.dto.UpdateCartRequest;
import id.ac.ui.cs.advprog.purchasepayment.exceptions.AppAlreadyInCheckoutException;
import id.ac.ui.cs.advprog.purchasepayment.ports.CheckoutRepository;
import id.ac.ui.cs.advprog.purchasepayment.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AppNotInCheckoutValidator extends Validator<UpdateCartRequest> {
    private CheckoutRepository checkoutRepository;
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
        return !checkoutRepository.existsAppInUserActiveCheckout(request.getUsername(), request.getId());
    }

    @Autowired
    public void setCheckoutRepository(CheckoutRepository checkoutRepository) {
        this.checkoutRepository = checkoutRepository;
    }
}
