package id.ac.ui.cs.advprog.purchasepayment.web.logic;

import id.ac.ui.cs.advprog.purchasepayment.dto.DeleteCartRequest;
import id.ac.ui.cs.advprog.purchasepayment.usecase.DeleteCart;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteCartLogicTest {
    @InjectMocks
    private DeleteCartLogic deleteCartLogic;
    @Mock
    private DeleteCart deleteCartImpl;

    private DeleteCartRequest request;

    @BeforeEach
    void setUp() {
        request = DeleteCartRequest.builder()
                .appId("<app_id>")
                .username("<username>")
                .build();
    }

    @Test
    void testProcessLogic() {
        deleteCartLogic.processLogic(request);
        verify(deleteCartImpl, times(1))
                .deleteCartByAppId(any(String.class), any(String.class));
    }
}
