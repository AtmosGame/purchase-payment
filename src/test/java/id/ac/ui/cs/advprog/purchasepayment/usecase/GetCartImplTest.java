package id.ac.ui.cs.advprog.purchasepayment.usecase;

import id.ac.ui.cs.advprog.purchasepayment.dto.GetCartResponse;
import id.ac.ui.cs.advprog.purchasepayment.exceptions.CartDoesNotExistException;
import id.ac.ui.cs.advprog.purchasepayment.models.Cart;
import id.ac.ui.cs.advprog.purchasepayment.models.CartDetails;
import id.ac.ui.cs.advprog.purchasepayment.ports.CartDetailsRepository;
import id.ac.ui.cs.advprog.purchasepayment.ports.CartRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class GetCartImplTest {
    @Spy
    @InjectMocks
    private GetCartImpl getCartImpl;
    @Mock
    private CartRepository cartRepository;
    @Mock
    private CartDetailsRepository cartDetailsRepository;

    private String username;

    private Cart userCart;
    private CartDetails cartDetails;

    @BeforeEach
    void setUp() {
        username = "<requestor_username>";

        userCart = Cart.builder()
                .id(1)
                .username(username)
                .build();

        cartDetails = CartDetails.builder()
                .id(1)
                .appId("<app_id>")
                .appName("<app_name>")
                .addDate(new Date())
                .appPrice(399399.0)
                .cart(userCart)
                .build();
    }

    @Test
    void testFindByUsernameAndFoundShouldReturnCart() {
        // arrange
        List<CartDetails> userCartDetails = List.of(cartDetails);
        when(cartRepository.findByUsername(username))
                .thenReturn(Optional.of(userCart));
        when(cartDetailsRepository.findAllByCartId(userCart.getId()))
                .thenReturn(userCartDetails);

        // act
        GetCartResponse response = getCartImpl.findCartByUsername(username);

        // assert
        verify(cartRepository, atLeastOnce())
                .findByUsername(any(String.class));
        verify(cartDetailsRepository, atLeastOnce())
                .findAllByCartId(any(Integer.class));
        GetCartResponse expectedResponse = GetCartResponse.fromCart(userCart, userCartDetails);
        Assertions.assertThat(response).isEqualTo(expectedResponse);
    }

    @Test
    void testFindCartByUsernameAndNotFoundShouldThrowException() {
        when(cartRepository.findByUsername(any(String.class)))
                .thenReturn(Optional.empty());

        String invalid_username = "<invalid_username>";
        Assertions.assertThatThrownBy(() -> getCartImpl.findCartByUsername(invalid_username))
                .isInstanceOf(CartDoesNotExistException.class)
                .hasMessageContaining("Cart with username " + invalid_username + " does not exist");
    }
}