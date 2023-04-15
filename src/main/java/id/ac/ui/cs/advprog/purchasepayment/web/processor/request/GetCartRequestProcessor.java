package id.ac.ui.cs.advprog.purchasepayment.web.processor.request;

import id.ac.ui.cs.advprog.purchasepayment.annotations.Processor;
import id.ac.ui.cs.advprog.purchasepayment.validation.Validator;
import jakarta.annotation.PostConstruct;
import lombok.Getter;

@Processor
@Getter
public class GetCartRequestProcessor implements RequestProcessor<Void> {
    private Validator<Void> validator;

    @PostConstruct
    public void init() {
        // TODO: Check if user is logged in
    }

    @Override
    public void validate(Void request) {
        // validator.isValid(request);
    }
}
