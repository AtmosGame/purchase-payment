package id.ac.ui.cs.advprog.purchasepayment.usecase;

import id.ac.ui.cs.advprog.purchasepayment.exceptions.AppNotInCartException;
import id.ac.ui.cs.advprog.purchasepayment.models.Cart;
import id.ac.ui.cs.advprog.purchasepayment.models.CartDetails;
import id.ac.ui.cs.advprog.purchasepayment.ports.CartDetailsRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteCartImplTest {
    @Spy
    @InjectMocks
    private DeleteCartImpl deleteCartImpl;
    @Mock
    private CartDetailsRepository cartDetailsRepository;

    private String username;
    private String appId;

    private Cart userCart;
    private CartDetails cartDetails;

    @BeforeEach
    void setUp() {
        username = "<requestor_username>";
        appId = "<app_id>";

        userCart = Cart.builder()
                .id(1)
                .username(username)
                .build();

        cartDetails = CartDetails.builder()
                .id(1)
                .appId(appId)
                .appName("<app_name>")
                .addDate(new Date())
                .appPrice(399399.0)
                .cart(userCart)
                .build();
    }

    @Test
    void testDeleteCartByAppIdAndFoundShouldSuccess() {
        when(cartDetailsRepository.deleteCartDetailsByCartUsernameAndAppId(username, appId)).thenReturn(1);

        // act
        deleteCartImpl.deleteCartByAppId(username, appId);

        // assert
        verify(cartDetailsRepository, times(1)).deleteCartDetailsByCartUsernameAndAppId(username, appId);
    }

    @Test
    void testDeleteCartByAppIdAndNotFoundShouldThrowException() {
        String invalidAppId = "<invalid_app_id>";
        when(cartDetailsRepository.deleteCartDetailsByCartUsernameAndAppId(username, invalidAppId)).thenReturn(0);

        // assert
        Assertions.assertThatThrownBy(() -> deleteCartImpl.deleteCartByAppId(username, invalidAppId))
                .isInstanceOf(AppNotInCartException.class)
                .hasMessageContaining(String.format("App with id:%s does not exist in cart", invalidAppId));
    }
}
