package id.ac.ui.cs.advprog.purchasepayment.web.logic;

import id.ac.ui.cs.advprog.purchasepayment.dto.CheckoutCartRequest;
import id.ac.ui.cs.advprog.purchasepayment.dto.UpdateCartRequest;
import id.ac.ui.cs.advprog.purchasepayment.usecase.CheckoutCart;
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
class CheckoutCartLogicTest {
    @InjectMocks
    private CheckoutCartLogic checkoutCartLogic;
    @Mock
    private RequestProcessor<CheckoutCartRequest> checkoutCartRequestProcessor;
    @Mock
    private CheckoutCart checkoutCartImpl;
    CheckoutCartRequest request;
    private String username;

    @BeforeEach
    void setUp() {
        username = "<requestor_username>";
        request = CheckoutCartRequest.builder()
                .id(1)
                .username(username)
                .build();
    }

    @Test
    void testProcessLogic() {
        checkoutCartLogic.processLogic(request);
        verify(checkoutCartRequestProcessor, times(1)).validate(request);
        verify(checkoutCartImpl, times(1)).checkout(request);
    }
}
