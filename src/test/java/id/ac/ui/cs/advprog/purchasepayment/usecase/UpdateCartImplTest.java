package id.ac.ui.cs.advprog.purchasepayment.usecase;

import id.ac.ui.cs.advprog.purchasepayment.dto.UpdateCartRequest;
import id.ac.ui.cs.advprog.purchasepayment.models.Cart;
import id.ac.ui.cs.advprog.purchasepayment.models.CartDetails;
import id.ac.ui.cs.advprog.purchasepayment.ports.CartDetailsRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UpdateCartImplTest {
    @Spy
    @InjectMocks
    private UpdateCartImpl updateCartImpl;
    @Mock
    private CartDetailsRepository cartDetailsRepository;
    @Mock
    private GetCart getCart;

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
        doReturn(userCart).when(getCart).getCartByUsername(request.getUsername());
        Cart result = updateCartImpl.update(request);
        Assertions.assertThat(userCart).isEqualTo(result);

    }

    @Test
    void testAddCartDetailsToCartByRequestWhenAppIsNotInCart() {
        when(cartDetailsRepository.save(any(CartDetails.class))).thenReturn(cartDetails);
        CartDetails result = updateCartImpl.addCartDetailsToCartByRequest(request, userCart);

        verify(cartDetailsRepository, times(1)).save(any(CartDetails.class));
        Assertions.assertThat(cartDetails).isEqualTo(result);
    }
}