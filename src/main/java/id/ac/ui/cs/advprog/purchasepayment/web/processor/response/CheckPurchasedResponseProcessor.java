package id.ac.ui.cs.advprog.purchasepayment.web.processor.response;

import id.ac.ui.cs.advprog.purchasepayment.annotations.Processor;
import id.ac.ui.cs.advprog.purchasepayment.dto.CheckPurchasedResponse;
import id.ac.ui.cs.advprog.purchasepayment.validation.checkpurchasedapps.response.CheckPurchasedResponseValidator;
import id.ac.ui.cs.advprog.purchasepayment.validation.Validator;
import lombok.Getter;

@Processor
@Getter
public class CheckPurchasedResponseProcessor implements ResponseProcessor<CheckPurchasedResponse, Boolean> {
    private Validator<CheckPurchasedResponse> validator = new CheckPurchasedResponseValidator();


    @Override
    public Boolean process(CheckPurchasedResponse response) {
        return validator.isValid(response);
    }
}
