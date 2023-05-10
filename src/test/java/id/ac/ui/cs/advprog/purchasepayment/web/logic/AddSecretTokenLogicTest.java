package id.ac.ui.cs.advprog.purchasepayment.web.logic;

import id.ac.ui.cs.advprog.purchasepayment.dto.AddSecretTokenRequest;
import id.ac.ui.cs.advprog.purchasepayment.dto.UpdateCartRequest;
import id.ac.ui.cs.advprog.purchasepayment.usecase.AddSecretToken;
import id.ac.ui.cs.advprog.purchasepayment.usecase.UpdateCart;
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
class AddSecretTokenLogicTest {

    @InjectMocks
    private AddSecretTokenLogic addSecretTokenLogic;
    @Mock
    private RequestProcessor<AddSecretTokenRequest> addSecretTokenRequestProcessor;
    @Mock
    private AddSecretToken addSecretTokenImpl;
    AddSecretTokenRequest request;

    @BeforeEach
    void setUp() {
        request = AddSecretTokenRequest.builder()
                .tokenName("token-name")
                .build();
    }

    @Test
    void testProcessLogic() {
        addSecretTokenLogic.processLogic(request);
        verify(addSecretTokenRequestProcessor, times(1)).validate(request);
        verify(addSecretTokenImpl, times(1)).addSecretTokenByRequest(request);
    }
}