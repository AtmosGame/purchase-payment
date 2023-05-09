package id.ac.ui.cs.advprog.purchasepayment.validation.updatecart.request;

import id.ac.ui.cs.advprog.purchasepayment.dto.UpdateCartRequest;
import id.ac.ui.cs.advprog.purchasepayment.validation.Validator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UpdateCartRequestValidatorFactoryImplTest {

    private UpdateCartRequestValidatorFactoryImpl factory;

    @BeforeEach
    void setUp() {
        factory = new UpdateCartRequestValidatorFactoryImpl();
    }

    @Test
    void testCreateUpdateCartRequestDataValidator() {
        Validator<UpdateCartRequest> updateCartRequestDataValidator = factory.createUpdateCartRequestDataValidator();
        Assertions.assertThat(updateCartRequestDataValidator).isInstanceOf(UpdateCartRequestDataValidator.class);
    }

    @Test
    void testCreateAppNotInPurchasedAppValidator() {
        Validator<UpdateCartRequest> appNotInPurchasedAppValidator = factory.createAppNotInPurchasedAppValidator();
        Assertions.assertThat(appNotInPurchasedAppValidator).isInstanceOf(AppNotInPurchasedAppValidator.class);
    }

    @Test
    void testCreateAppNotInCartValidator() {
        Validator<UpdateCartRequest> appNotInCartValidator = factory.createAppNotInCartValidator();
        Assertions.assertThat(appNotInCartValidator).isInstanceOf(AppNotInCartValidator.class);
    }

    @Test
    void testCreateAppNotInCheckoutValidator() {
        Validator<UpdateCartRequest> appNotInCheckoutValidator = factory.createAppNotInCheckoutValidator();
        Assertions.assertThat(appNotInCheckoutValidator).isInstanceOf(AppNotInCheckoutValidator.class);
    }
}