package id.ac.ui.cs.advprog.purchasepayment.validation.updatecart.request;

import id.ac.ui.cs.advprog.purchasepayment.dto.UpdateCartRequest;
import id.ac.ui.cs.advprog.purchasepayment.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class UpdateCartRequestValidatorTest {
    private Validator<UpdateCartRequest> appNotInCartValidator;
    private Validator<UpdateCartRequest> appNotInCheckoutValidator;
    private Validator<UpdateCartRequest> appNotInListValidator;
    private Validator<UpdateCartRequest> updateCartRequestDataValidator;
    private UpdateCartRequest request;

    @BeforeEach
    void setUp() {
        UpdateCartRequestValidatorFactory factory = new UpdateCartRequestValidatorFactoryImpl();
        updateCartRequestDataValidator = factory.createUpdateCartRequestDataValidator();
        appNotInListValidator = factory.createAppNotInListValidator();
        appNotInCartValidator = factory.createAppNotInCartValidator();
        appNotInCheckoutValidator = factory.createAppNotInCheckoutValidator();

        request = UpdateCartRequest.builder()
                .id("<app_id>")
                .name("<app_name>")
                .price(0.0)
                .username("<requestor_username>")
                .build();
    }

    @Test
    void testIsValidWithoutNextValidator() {
        Assertions.assertAll(
                "Valid with next validator is null",
                () -> {
                    assertThat(updateCartRequestDataValidator.isValid(request)).isTrue();
                    assertThat(appNotInListValidator.isValid(request)).isTrue();
                    assertThat(appNotInCartValidator.isValid(request)).isTrue();
                    assertThat(appNotInCheckoutValidator.isValid(request)).isTrue();
                }
        );
    }

    @Test
    void testIsValidWithNextValidator() {
        updateCartRequestDataValidator.setNextValidator(appNotInListValidator);
        appNotInListValidator.setNextValidator(appNotInCartValidator);
        appNotInCartValidator.setNextValidator(appNotInCheckoutValidator);
        appNotInCheckoutValidator.setNextValidator(new AppNotInCheckoutValidator());

        Validator<UpdateCartRequest> validator = updateCartRequestDataValidator;

        Assertions.assertAll(
                "Valid with next validator is not null",
                () -> {
                    assertThat(validator.isValid(request)).isTrue();
                    assertThat(updateCartRequestDataValidator.isValid(request)).isTrue();
                    assertThat(appNotInListValidator.isValid(request)).isTrue();
                    assertThat(appNotInCartValidator.isValid(request)).isTrue();
                    assertThat(appNotInCheckoutValidator.isValid(request)).isTrue();
                }
        );
    }
}