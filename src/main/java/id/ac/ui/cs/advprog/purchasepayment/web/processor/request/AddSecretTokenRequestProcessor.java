package id.ac.ui.cs.advprog.purchasepayment.web.processor.request;


import id.ac.ui.cs.advprog.purchasepayment.annotations.Processor;
import id.ac.ui.cs.advprog.purchasepayment.dto.AddSecretTokenRequest;
import id.ac.ui.cs.advprog.purchasepayment.validation.Validator;

@Processor
public class AddSecretTokenRequestProcessor implements RequestProcessor<AddSecretTokenRequest> {
    private Validator<AddSecretTokenRequest> validator;

    @Override
    public void validate(AddSecretTokenRequest request) {
        validator.isValid(request);
    }
}
