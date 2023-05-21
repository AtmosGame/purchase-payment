package id.ac.ui.cs.advprog.purchasepayment.web.processor.response;

import id.ac.ui.cs.advprog.purchasepayment.dto.AddSecretTokenRequest;
import id.ac.ui.cs.advprog.purchasepayment.dto.CheckPurchasedResponse;
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
class CheckPurchasedResponseProcessorTest {
    @InjectMocks
    private CheckPurchasedResponseProcessor checkPurchasedResponseProcessor;
    @Mock
    private Validator<CheckPurchasedResponseProcessor> validator;
    private CheckPurchasedResponse response;

    @BeforeEach
    void setUp() {
        response = CheckPurchasedResponse.builder()
                .isPurchased(true)
                .build();
    }

    @Test
    void testValidate() {
        checkPurchasedResponseProcessor.process(response);
        verify(checkPurchasedResponseProcessor.getValidator(), times(1)).isValid(response);
    }
}