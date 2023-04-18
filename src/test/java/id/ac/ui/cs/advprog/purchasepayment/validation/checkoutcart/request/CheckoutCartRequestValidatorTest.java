package id.ac.ui.cs.advprog.purchasepayment.validation.checkoutcart.request;

import id.ac.ui.cs.advprog.purchasepayment.dto.CheckoutCartRequest;
import id.ac.ui.cs.advprog.purchasepayment.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class CheckoutCartRequestValidatorTest {
    private Validator<CheckoutCartRequest> checkoutCartRequestDataValidator;
    private Validator<CheckoutCartRequest> appNotInListValidator;
    private Validator<CheckoutCartRequest> appNotInCartValidator;
    private Validator<CheckoutCartRequest> appNotInCheckoutValidator;
    private CheckoutCartRequest request;

    @BeforeEach
    void setUp() {
        CheckoutCartRequestValidatorFactory factory = new CheckoutCartRequestValidatorFactoryImpl();
        checkoutCartRequestDataValidator = factory.createCheckoutCartRequestDataValidator();
        appNotInListValidator = factory.createAppNotInListValidator();
        appNotInCartValidator = factory.createAppNotInCartValidator();
        appNotInCheckoutValidator = factory.createAppNotInCheckoutValidator();

        request = CheckoutCartRequest.builder()
                .id(1)
                .username("<requestor_username>")
                .build();
    }

    @Test
    void testIsValidWithoutNextValidator() {
        Assertions.assertAll(
                "Valid with next validator is null",
                () -> {
                    assertThat(checkoutCartRequestDataValidator.isValid(request)).isTrue();
                    assertThat(appNotInListValidator.isValid(request)).isTrue();
                    assertThat(appNotInCartValidator.isValid(request)).isTrue();
                    assertThat(appNotInCheckoutValidator.isValid(request)).isTrue();
                }
        );
    }

    @Test
    void testIsValidWithNextValidator() {
        checkoutCartRequestDataValidator.setNextValidator(appNotInListValidator);
        appNotInListValidator.setNextValidator(appNotInCartValidator);
        appNotInCartValidator.setNextValidator(appNotInCheckoutValidator);
        appNotInCheckoutValidator.setNextValidator(new AppNotInCheckoutValidator());

        Validator<CheckoutCartRequest> validator = checkoutCartRequestDataValidator;

        Assertions.assertAll(
                "Valid with next validator is not null",
                () -> {
                    assertThat(validator.isValid(request)).isTrue();
                }
        );
    }
}
