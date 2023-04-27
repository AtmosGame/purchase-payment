package id.ac.ui.cs.advprog.purchasepayment.web.processor.request;

import id.ac.ui.cs.advprog.purchasepayment.dto.UpdatePaymentRequest;
import id.ac.ui.cs.advprog.purchasepayment.validation.Validator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UpdatePaymentRequestProcessorTest {
    @InjectMocks
    private UpdatePaymentRequestProcessor updatePaymentRequestProcessor;
    @Mock
    private Validator<UpdatePaymentRequest> validator;
    @Mock
    private Validator<UpdatePaymentRequest> secretTokenValidator;
    private UpdatePaymentRequest request;

    @BeforeEach
    void setUp() {
        request = UpdatePaymentRequest.builder()
                .id("<checkout_id>")
                .token("<secret_token>")
                .build();
    }

    @Test
    void testValidate() {
        updatePaymentRequestProcessor.validate(request);
        verify(updatePaymentRequestProcessor.getValidator(), times(1)).isValid(request);
    }

    @Test
    void testInit() {
        updatePaymentRequestProcessor.init();
        Assertions.assertThat(updatePaymentRequestProcessor.getValidator()).isNotNull();
    }

}