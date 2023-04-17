package id.ac.ui.cs.advprog.purchasepayment.web.logic;

import id.ac.ui.cs.advprog.purchasepayment.dto.UpdatePaymentRequest;
import id.ac.ui.cs.advprog.purchasepayment.usecase.UpdatePayment;
import id.ac.ui.cs.advprog.purchasepayment.web.processor.request.RequestProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UpdatePaymentLogicTest {
    @InjectMocks
    private UpdatePaymentLogic updatePaymentLogic;
    @Mock
    private RequestProcessor<UpdatePaymentRequest> updatePaymentRequestProcessor;
    @Mock
    private UpdatePayment updatePaymentImpl;
    UpdatePaymentRequest request;

    @BeforeEach
    void setUp() {
        request = UpdatePaymentRequest.builder()
                .id("<checkout_id>")
                .token("<secret_token>")
                .build();
    }

    @Test
    void testProcessLogic() {
        updatePaymentLogic.processLogic(request);
        verify(updatePaymentRequestProcessor, times(1)).validate(request);
        verify(updatePaymentImpl, times(1)).update(request.getId());
    }
}