package id.ac.ui.cs.advprog.purchasepayment.validation.updatecart.request;

import id.ac.ui.cs.advprog.purchasepayment.dto.AppValidationResponse;
import id.ac.ui.cs.advprog.purchasepayment.dto.UpdateCartRequest;
import id.ac.ui.cs.advprog.purchasepayment.exceptions.RequestDataInvalidException;
import id.ac.ui.cs.advprog.purchasepayment.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class UpdateCartRequestDataValidator extends Validator<UpdateCartRequest> {
    @Qualifier("gamesAppsStore")
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
        try {
            Mono<ResponseEntity<AppValidationResponse>> chekAppDataMono = checkAppDataAsync(request);
            ResponseEntity<AppValidationResponse> response = chekAppDataMono.block();

            if (response == null) {
                return false;
            }

            AppValidationResponse responseBody = response.getBody();

            if (responseBody == null) {
                return false;
            }

            return responseBody.getIsValid();
        } catch (Exception e) {
            return false;
        }
    }

    public Mono<ResponseEntity<AppValidationResponse>> checkAppDataAsync(UpdateCartRequest request) {
        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
        formData.add("id", Integer.valueOf(request.getId()));
        formData.add("name", request.getName());
        formData.add("price", request.getPrice());

        return webClient.method(HttpMethod.GET)
                .uri("/app-detail/validation")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE)
                .bodyValue(formData)
                .retrieve()
                .onStatus(
                        status -> status.value() != 200,
                        error -> Mono.error(new RequestDataInvalidException())
                )
                .toEntity(AppValidationResponse.class);
    }

    @Autowired
    public void setWebClient(WebClient webClient) {
        this.webClient = webClient;
    }
}
