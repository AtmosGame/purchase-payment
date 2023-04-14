package id.ac.ui.cs.advprog.purchasepayment.validation.AddSecretToken;

import id.ac.ui.cs.advprog.purchasepayment.dto.AddSecretTokenRequest;
import id.ac.ui.cs.advprog.purchasepayment.dto.UpdateCartRequest;
import id.ac.ui.cs.advprog.purchasepayment.validation.Validator;

public class AddSecretTokenValidator extends Validator<AddSecretTokenRequest> {

    @Override
    public boolean isValid(AddSecretTokenRequest request) {
        if(request == null){
            throw new RuntimeException("Token cannot be null");
        }
        return true;
    }
}
