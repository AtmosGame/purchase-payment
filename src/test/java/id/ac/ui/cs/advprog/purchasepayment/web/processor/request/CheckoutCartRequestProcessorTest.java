package id.ac.ui.cs.advprog.purchasepayment.web.processor.request;

import id.ac.ui.cs.advprog.purchasepayment.dto.CheckoutCartRequest;
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
class CheckoutCartRequestProcessorTest {
    @InjectMocks
    private CheckoutCartRequestProcessor checkoutCartRequestProcessor;
    @Mock
    private Validator<CheckoutCartRequest> validator;
    private CheckoutCartRequest request;
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
    void testValidate() {
        checkoutCartRequestProcessor.validate(request);
        verify(checkoutCartRequestProcessor.getValidator(), times(1)).isValid(request);
    }

    @Test
    void testInit() {
        checkoutCartRequestProcessor.init();
        Assertions.assertThat(checkoutCartRequestProcessor.getValidator()).isNotNull();
    }
}
