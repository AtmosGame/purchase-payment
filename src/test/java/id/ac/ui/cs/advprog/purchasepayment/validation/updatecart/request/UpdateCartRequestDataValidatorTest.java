package id.ac.ui.cs.advprog.purchasepayment.validation.updatecart.request;

import id.ac.ui.cs.advprog.purchasepayment.dto.AppValidationResponse;
import id.ac.ui.cs.advprog.purchasepayment.dto.UpdateCartRequest;
import id.ac.ui.cs.advprog.purchasepayment.exceptions.RequestDataInvalidException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateCartRequestDataValidatorTest {
    @Spy
    @InjectMocks
    private UpdateCartRequestDataValidator updateCartRequestDataValidator;
    @Mock
    private WebClient webClient;
    @Mock
    private WebClient.RequestBodyUriSpec requestBodyUriSpec;
    @Mock
    private WebClient.RequestBodySpec requestBodySpec;
    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;
    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;
    @Mock
    private WebClient.ResponseSpec responseSpec;
    private UpdateCartRequest request;

    @BeforeEach
    void setUp() {
        request = UpdateCartRequest.builder()
                .id("1")
                .name("<app_name>")
                .price(0.0)
                .username("<requestor_username>")
                .build();
    }

    @Test
    void testIsValidReturnTrueWithNextValidatorNull() {
        doReturn(true).when(updateCartRequestDataValidator).requestDataValid(request);
        Assertions.assertThat(updateCartRequestDataValidator.isValid(request)).isTrue();
    }

    @Test
    void testIsValidReturnTrueWithNextValidatorNotNull() {
        UpdateCartRequestDataValidator nextValidator = Mockito.spy(UpdateCartRequestDataValidator.class);
        updateCartRequestDataValidator.setNextValidator(nextValidator);

        doReturn(true).when(updateCartRequestDataValidator).requestDataValid(request);
        doReturn(true).when(nextValidator).requestDataValid(request);

        Assertions.assertThat(updateCartRequestDataValidator.isValid(request)).isTrue();
    }

    @Test
    void testIsValidThrowError() {
        doReturn(false).when(updateCartRequestDataValidator).requestDataValid(request);
        Assertions.assertThatThrownBy(() -> updateCartRequestDataValidator.isValid(request))
                .isInstanceOf(RequestDataInvalidException.class)
                .hasMessage("Request data is not valid");
    }

    @Test
    void testCheckAppDataAsyncIsOK() {
        lenient().when(webClient.method(HttpMethod.GET)).thenReturn(requestBodyUriSpec);
        lenient().when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodySpec);
        lenient().when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        lenient().when(requestBodySpec.header(any(), any())).thenReturn(requestBodySpec);
        lenient().when(requestBodySpec.bodyValue(any())).thenReturn(requestHeadersSpec);
        lenient().when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        lenient().when(responseSpec.onStatus(any(), any())).thenReturn(responseSpec);
        lenient().when(responseSpec.toEntity(AppValidationResponse.class)).thenReturn(Mono.just(mock(ResponseEntity.class)));

        Mono<ResponseEntity<AppValidationResponse>> mono = updateCartRequestDataValidator.checkAppDataAsync(request);
        Assertions.assertThat(mono).isNotNull();
    }

    @Test
    void testCheckAppDataAsyncThrowError() {
        lenient().when(webClient.method(HttpMethod.GET)).thenReturn(requestBodyUriSpec);
        lenient().when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodySpec);
        lenient().when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        lenient().when(requestBodySpec.header(any(), any())).thenReturn(requestBodySpec);
        lenient().when(requestBodySpec.bodyValue(any())).thenReturn(requestHeadersSpec);
        lenient().when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        lenient().when(responseSpec.onStatus(any(), any())).thenThrow(new RequestDataInvalidException());

        Assertions.assertThatThrownBy(() -> updateCartRequestDataValidator.checkAppDataAsync(request))
                .isInstanceOf(RequestDataInvalidException.class)
                .hasMessage("Request data is not valid");
    }

}