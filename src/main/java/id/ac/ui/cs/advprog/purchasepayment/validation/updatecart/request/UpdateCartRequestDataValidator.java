package id.ac.ui.cs.advprog.purchasepayment.validation.updatecart.request;

import id.ac.ui.cs.advprog.purchasepayment.dto.UpdateCartRequest;
import id.ac.ui.cs.advprog.purchasepayment.exceptions.RequestDataInvalidException;
import id.ac.ui.cs.advprog.purchasepayment.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class UpdateCartRequestDataValidator extends Validator<UpdateCartRequest> {
    private WebClient webClient;
    @Override
    public boolean isValid(UpdateCartRequest request) {
        if (requestDataValid(request)) {
            nextValidatorIsValid(request);
        } else {
            throw new RequestDataInvalidException();
        }

        return true;
    }

    public boolean requestDataValid(UpdateCartRequest request) {
        Mono<ResponseEntity<String>> chekAppDataMono = checkAppDataAsync(request);
        chekAppDataMono.block();
        return true;
    }

    public Mono<ResponseEntity<String>> checkAppDataAsync(UpdateCartRequest request) {
        return webClient.get()
                .uri("/api/v1/dummy/hello")
                .retrieve()
                .onStatus(
                        status -> status.value() != 200,
                        error -> Mono.error(new RequestDataInvalidException())
                )
                .toEntity(String.class);
    }

    @Autowired
    public void setWebClient(WebClient webClient) {
        this.webClient = webClient;
    }
}
