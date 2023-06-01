package id.ac.ui.cs.advprog.purchasepayment.web.processor.request;

import id.ac.ui.cs.advprog.purchasepayment.dto.AddSecretTokenRequest;
import id.ac.ui.cs.advprog.purchasepayment.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AddSecretTokenRequestProcessorTest {
    @InjectMocks
    private AddSecretTokenRequestProcessor addSecretTokenRequestProcessor;
    @Mock
    private Validator<AddSecretTokenRequest> validator;
    private AddSecretTokenRequest request;

    @BeforeEach
    void setUp() {
        request = AddSecretTokenRequest.builder()
                .tokenName("token-name")
                .build();
    }

    @Test
    void testValidate() {
        addSecretTokenRequestProcessor.validate(request);
        verify(addSecretTokenRequestProcessor.getValidator(), times(1)).isValid(request);
    }


}