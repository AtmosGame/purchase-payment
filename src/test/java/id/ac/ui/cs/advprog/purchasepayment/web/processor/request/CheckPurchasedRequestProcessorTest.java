package id.ac.ui.cs.advprog.purchasepayment.web.processor.request;

import id.ac.ui.cs.advprog.purchasepayment.dto.CheckPurchasedRequest;
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
class CheckPurchasedRequestProcessorTest {
    @InjectMocks
    private CheckPurchasedRequestProcessor checkPurchasedRequestProcessor;
    @Mock
    private Validator<CheckPurchasedRequest> validator;
    private CheckPurchasedRequest request;

    @BeforeEach
    void setUp() {
        request = CheckPurchasedRequest.builder()
                .username("user-id")
                .appId("app-id")
                .build();
    }

    @Test
    void testValidate() {
        checkPurchasedRequestProcessor.validate(request);
        verify(checkPurchasedRequestProcessor.getValidator(), times(1)).isValid(request);
    }


}