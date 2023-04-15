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
class GetCartRequestProcessorTest {
    @InjectMocks
    private GetCartRequestProcessor getCartRequestProcessor;
    @Mock
    private Validator<Void> validator;

    private Void request;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testValidate() {
        getCartRequestProcessor.validate(request);
        // INFO: Waiting for validator to be finalized
        // verify(getCartRequestProcessor.getValidator(), times(1)).isValid(request);
    }

    @Test
    void testInit() {
        getCartRequestProcessor.init();
        System.out.println(getCartRequestProcessor.getValidator());
        // INFO: Waiting for validator to be finalized
        // Assertions.assertThat(getCartRequestProcessor.getValidator()).isNotNull();
    }
}