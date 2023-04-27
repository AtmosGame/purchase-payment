package id.ac.ui.cs.advprog.purchasepayment.web.processor.request;

import id.ac.ui.cs.advprog.purchasepayment.dto.UpdateCartRequest;
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
class UpdateCartRequestProcessorTest {
    @InjectMocks
    private UpdateCartRequestProcessor updateCartRequestProcessor;
    @Mock
    private Validator<UpdateCartRequest> validator;
    @Mock
    private Validator<UpdateCartRequest> appNotInCartValidator;
    private UpdateCartRequest request;

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
    void testValidate() {
        updateCartRequestProcessor.validate(request);
        verify(updateCartRequestProcessor.getValidator(), times(1)).isValid(request);
    }

    @Test
    void testInit() {
        updateCartRequestProcessor.init();
        Assertions.assertThat(updateCartRequestProcessor.getValidator()).isNotNull();
    }
}