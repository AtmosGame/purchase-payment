package id.ac.ui.cs.advprog.purchasepayment.web.logic;

import id.ac.ui.cs.advprog.purchasepayment.dto.AddSecretTokenRequest;
import id.ac.ui.cs.advprog.purchasepayment.dto.CheckPurchasedRequest;
import id.ac.ui.cs.advprog.purchasepayment.dto.CheckPurchasedResponse;
import id.ac.ui.cs.advprog.purchasepayment.usecase.AddSecretToken;
import id.ac.ui.cs.advprog.purchasepayment.usecase.CheckPurchasedApp;
import id.ac.ui.cs.advprog.purchasepayment.validation.checkpurchasedapps.request.CheckPurchasedRequestValidator;
import id.ac.ui.cs.advprog.purchasepayment.web.processor.request.RequestProcessor;
import id.ac.ui.cs.advprog.purchasepayment.web.processor.response.CheckPurchasedResponseProcessor;
import id.ac.ui.cs.advprog.purchasepayment.web.processor.response.ResponseProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CheckPurchasedLogicTest {

    @InjectMocks
    private CheckPurchasedLogic checkPurchasedAppLogic;
    @Mock
    private RequestProcessor<CheckPurchasedRequest> CheckPurchasedRequestProcessor;

    @Mock
    private ResponseProcessor<CheckPurchasedResponse, Boolean> checkPurchasedResponseProcessor;
    @Mock
    private CheckPurchasedApp checkPurchasedAppImpl;
    CheckPurchasedRequest request;

    @BeforeEach
    void setUp() {
        request = CheckPurchasedRequest.builder()
                .appId("appId")
                .username("username")
                .build();


    }

    @Test
    void testProcessLogic() {
        checkPurchasedAppLogic.processLogic(request);
        verify(CheckPurchasedRequestProcessor, times(1)).validate(request);
        verify(checkPurchasedAppImpl, times(1)).isPurchased(request);
    }
}