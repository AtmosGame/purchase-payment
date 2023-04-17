package id.ac.ui.cs.advprog.purchasepayment.web.logic;

import id.ac.ui.cs.advprog.purchasepayment.usecase.GetCart;
import id.ac.ui.cs.advprog.purchasepayment.web.processor.request.RequestProcessor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class GetCartLogicTest {
    @InjectMocks
    private GetCartLogic getCartLogic;
    @Mock
    private RequestProcessor<Void> getCartRequestProcessor;
    @Mock
    private GetCart getCartImpl;

    private Void request;

    @BeforeEach
    void setUp() {
    }

    @Test
     void testProcessLogic() {
        getCartLogic.processLogic(request);
        verify(getCartRequestProcessor, times(1)).validate(request);
        verify(getCartImpl, times(1)).findCartByUsername(any(String.class));
    }
}
