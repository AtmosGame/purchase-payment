package id.ac.ui.cs.advprog.purchasepayment.validation.updatepayment.request;

import id.ac.ui.cs.advprog.purchasepayment.dto.UpdatePaymentRequest;
import id.ac.ui.cs.advprog.purchasepayment.validation.Validator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UpdatePaymentRequestFactoryImplTest {

    private UpdatePaymentRequestFactoryImpl factory;

    @BeforeEach
    void setUp() {
        factory = new UpdatePaymentRequestFactoryImpl();
    }

    @Test
    void testCreateSecretTokenValidator() {
        Validator<UpdatePaymentRequest> updateCartRequestDataValidator = factory.createSecretTokenValidator();
        Assertions.assertThat(updateCartRequestDataValidator).isInstanceOf(SecretTokenValidator.class);
    }

    @Test
    void testCreateCheckoutNotExpiredValidator() {
        Validator<UpdatePaymentRequest> appNotInListValidator = factory.createCheckoutNotExpiredValidator();
        Assertions.assertThat(appNotInListValidator).isInstanceOf(CheckoutNotExpiredValidator.class);
    }
}
