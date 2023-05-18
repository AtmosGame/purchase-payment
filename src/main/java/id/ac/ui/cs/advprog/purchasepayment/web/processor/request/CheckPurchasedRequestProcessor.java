package id.ac.ui.cs.advprog.purchasepayment.web.processor.request;

import id.ac.ui.cs.advprog.purchasepayment.annotations.Processor;
import id.ac.ui.cs.advprog.purchasepayment.dto.CheckPurchasedRequest;
import id.ac.ui.cs.advprog.purchasepayment.validation.checkpurchasedapps.request.CheckPurchasedRequestValidator;
import id.ac.ui.cs.advprog.purchasepayment.validation.Validator;
import lombok.Getter;

@Processor
@Getter
public class CheckPurchasedRequestProcessor implements RequestProcessor<CheckPurchasedRequest> {
    private Validator<CheckPurchasedRequest> validator = new CheckPurchasedRequestValidator();

    public void validate(CheckPurchasedRequest request) {
        validator.isValid(request);
    }
}
