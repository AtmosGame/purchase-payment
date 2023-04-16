package id.ac.ui.cs.advprog.purchasepayment.web.processor.request;


import id.ac.ui.cs.advprog.purchasepayment.annotations.Processor;
import id.ac.ui.cs.advprog.purchasepayment.dto.AddSecretTokenRequest;
import id.ac.ui.cs.advprog.purchasepayment.validation.addsecrettoken.request.AddSecretTokenValidator;
import id.ac.ui.cs.advprog.purchasepayment.validation.Validator;
import lombok.Getter;

@Processor
@Getter
public class AddSecretTokenRequestProcessor implements RequestProcessor<AddSecretTokenRequest> {
    private Validator<AddSecretTokenRequest> validator = new AddSecretTokenValidator();

    @Override
    public void validate(AddSecretTokenRequest request) {
        validator.isValid(request);
    }
}
