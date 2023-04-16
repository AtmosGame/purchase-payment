package id.ac.ui.cs.advprog.purchasepayment.validation.addsecrettoken.request;

import id.ac.ui.cs.advprog.purchasepayment.dto.AddSecretTokenRequest;
import id.ac.ui.cs.advprog.purchasepayment.exceptions.EmptySecretTokenException;
import id.ac.ui.cs.advprog.purchasepayment.validation.Validator;

public class AddSecretTokenValidator extends Validator<AddSecretTokenRequest> {

    @Override
    public boolean isValid(AddSecretTokenRequest request) {
        if(request.getTokenName().equals("")){
            throw new EmptySecretTokenException();
        }
        return true;
    }
}
