package id.ac.ui.cs.advprog.purchasepayment.usecase;

import id.ac.ui.cs.advprog.purchasepayment.dto.UpdateCartRequest;
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
import java.util.Optional;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UpdateCartImplTest {
    @Spy
    @InjectMocks
    private UpdateCartImpl updateCartImpl;
    @Mock
    private CartRepository cartRepository;
    @Mock
    private CartDetailsRepository cartDetailsRepository;

    private UpdateCartRequest request;

    private Cart userCart;
    private CartDetails cartDetails;

    @BeforeEach
    void setUp() {
        request = UpdateCartRequest.builder()
                .id("<app_id>")
                .name("<app_name>")
                .price(0.0)
                .username("<requestor_username>")
                .build();

        userCart = Cart.builder()
                .id(1)
                .username(request.getUsername())
                .build();

        cartDetails = CartDetails.builder()
                .id(1)
                .appId(request.getId())
                .appName(request.getName())
                .addDate(new Date())
                .appPrice(request.getPrice())
                .cart(userCart)
                .build();
    }

    @Test
    void testUpdate() {
        doReturn(userCart).when(updateCartImpl).getCartByUsername(request.getUsername());
        Cart result = updateCartImpl.update(request);
        Assertions.assertThat(userCart).isEqualTo(result);

    }

    @Test
    void testGetCartByUsernameWithNonEmptyOptional() {
        Optional<Cart> expected = Optional.of(userCart);
        doReturn(expected).when(updateCartImpl).findCartByUsername(request.getUsername());

        Cart result = updateCartImpl.getCartByUsername(request.getUsername());
        Assertions.assertThat(userCart).isEqualTo(result);
    }

    @Test
    void testGetCartByUsernameWithEmptyOptional() {
        Optional<Cart> expected = Optional.empty();
        doReturn(expected).when(updateCartImpl).findCartByUsername(request.getUsername());

        updateCartImpl.getCartByUsername(request.getUsername());
        verify(cartRepository, times(1)).save(any(Cart.class));
    }

    @Test
    void testFindCartByUsernameWithNonEmptyOptional() {
        Optional<Cart> expected = Optional.of(userCart);
        when(cartRepository.findByUsername(request.getUsername())).thenReturn(expected);

        Optional<Cart> result = updateCartImpl.findCartByUsername(request.getUsername());
        Assertions.assertThat(expected).isEqualTo(result);
    }

    @Test
    void testAddCartDetailsToCartByRequestWhenAppIsNotInCart() {
        doReturn(true).when(updateCartImpl).isAppNotInCart(request);
        when(cartDetailsRepository.save(any(CartDetails.class))).thenReturn(cartDetails);
        CartDetails result = updateCartImpl.addCartDetailsToCartByRequest(request, userCart);

        verify(cartDetailsRepository, times(1)).save(any(CartDetails.class));
        Assertions.assertThat(cartDetails).isEqualTo(result);
    }

    @Test
    void testAddCartDetailsToCartByRequestWhenAppIsInCart() {
        doReturn(false).when(updateCartImpl).isAppNotInCart(request);
        Assertions.assertThatThrownBy(() -> {
            updateCartImpl.addCartDetailsToCartByRequest(request, userCart);
        }).isInstanceOf(RuntimeException.class).hasMessage("App already exist in cart");
    }

    @Test
    void testFindCartDetailsByCartUsernameAndAppId() {
        Optional<CartDetails> expected = Optional.of(cartDetails);
        when(cartDetailsRepository.findByCartUsernameAndAppId(request.getUsername(), request.getId()))
                .thenReturn(expected);

        Optional<CartDetails> result = updateCartImpl.findCartDetailsByCartUsernameAndAppId(request.getUsername(), request.getId());
        Assertions.assertThat(expected).isEqualTo(result);
    }

    @Test
    void testIsAppNotInCartTrue() {
        doReturn(Optional.empty()).when(updateCartImpl).findCartDetailsByCartUsernameAndAppId(
                request.getUsername(),
                request.getId());
        boolean result = updateCartImpl.isAppNotInCart(request);
        Assertions.assertThat(result).isTrue();
    }
}