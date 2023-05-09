package id.ac.ui.cs.advprog.purchasepayment.validation.updatecart.request;

import id.ac.ui.cs.advprog.purchasepayment.dto.UpdateCartRequest;
import id.ac.ui.cs.advprog.purchasepayment.exceptions.AppAlreadyInPurchasedAppException;
import id.ac.ui.cs.advprog.purchasepayment.ports.PurchasedAppRepository;
import id.ac.ui.cs.advprog.purchasepayment.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AppNotInPurchasedAppValidator extends Validator<UpdateCartRequest> {
    private PurchasedAppRepository purchasedAppRepository;
    @Override
    public boolean isValid(UpdateCartRequest request) {
        if (appNotInPurchasedApp(request)) {
            nextValidatorIsValid(request);
        } else {
            throw new AppAlreadyInPurchasedAppException(request.getName(), request.getId());
        }

        return true;
    }

    public boolean appNotInPurchasedApp(UpdateCartRequest request) {
        return !purchasedAppRepository.existsByUsernameAndAppId(request.getUsername(), request.getId());
    }

    @Autowired
    public void setPurchasedAppRepository(PurchasedAppRepository purchasedAppRepository) {
        this.purchasedAppRepository = purchasedAppRepository;
    }
}
