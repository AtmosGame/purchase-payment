package id.ac.ui.cs.advprog.purchasepayment.web.logic;

import id.ac.ui.cs.advprog.purchasepayment.dto.UpdateCartRequest;
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
class UpdateCartLogicTest {

    @InjectMocks
    private UpdateCartLogic updateCartLogic;
    @Mock
    private RequestProcessor<UpdateCartRequest> updateCartRequestProcessor;
    @Mock
    private UpdateCart updateCartImpl;
    UpdateCartRequest request;

    @BeforeEach
    void setUp() {
        request = UpdateCartRequest.builder()
                .id("<app_id>")
                .name("<app_name>")
                .price(0.0)
                .username("<requestor_username>")
                .build();
    }

    @Test
    void testProccesLogic() {
        updateCartLogic.processLogic(request);
        verify(updateCartRequestProcessor, times(1)).validate(request);
        verify(updateCartImpl, times(1)).update(request);
    }

}