package id.ac.ui.cs.advprog.purchasepayment.validation.updatecart.request;

import id.ac.ui.cs.advprog.purchasepayment.dto.UpdateCartRequest;
import id.ac.ui.cs.advprog.purchasepayment.exceptions.AppAlreadyInCartException;
import id.ac.ui.cs.advprog.purchasepayment.ports.CartDetailsRepository;
import id.ac.ui.cs.advprog.purchasepayment.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AppNotInCartValidator extends Validator<UpdateCartRequest> {
    private CartDetailsRepository cartDetailsRepository;

    @Autowired
    public void setCartDetailsRepository(CartDetailsRepository cartDetailsRepository) {
        this.cartDetailsRepository = cartDetailsRepository;
    }

    @Override
    public boolean isValid(UpdateCartRequest request) {
        if (appNotInCart(request)) {
            nextValidatorIsValid(request);
        } else {
            throw new AppAlreadyInCartException(request.getName(), request.getId());
        }

        return true;
    }

    public boolean appNotInCart(UpdateCartRequest request) {
        return !cartDetailsRepository.existsByCartUsernameAndAppId(request.getUsername(), request.getId());
    }
}
