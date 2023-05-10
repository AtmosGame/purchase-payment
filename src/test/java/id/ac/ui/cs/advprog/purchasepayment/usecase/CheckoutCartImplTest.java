package id.ac.ui.cs.advprog.purchasepayment.usecase;

import id.ac.ui.cs.advprog.purchasepayment.dto.CheckoutCartRequest;
import id.ac.ui.cs.advprog.purchasepayment.dto.GetCartResponse;
import id.ac.ui.cs.advprog.purchasepayment.dto.UpdateCartRequest;
import id.ac.ui.cs.advprog.purchasepayment.exceptions.AppAlreadyInCartException;
import id.ac.ui.cs.advprog.purchasepayment.models.Cart;
import id.ac.ui.cs.advprog.purchasepayment.models.CartDetails;
import id.ac.ui.cs.advprog.purchasepayment.models.Checkout;
import id.ac.ui.cs.advprog.purchasepayment.ports.CartDetailsRepository;
import id.ac.ui.cs.advprog.purchasepayment.ports.CartRepository;
import id.ac.ui.cs.advprog.purchasepayment.ports.CheckoutRepository;
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
class CheckoutCartImplTest {
    private CheckoutCartRequest request;
    private Cart userCart;
    private CartDetails cartDetails;
    @Mock
    private CheckoutRepository checkoutRepository;

    @Spy
    @InjectMocks
    private CheckoutCartImpl checkoutCartImpl;

    @Spy
    @InjectMocks
    private GetCartImpl getCartImpl;
    @Mock
    private CartRepository cartRepository;
    @Mock
    private CartDetailsRepository cartDetailsRepository;
    private String username;
    private Checkout userCheckout;

    @BeforeEach
    void setUp() {
        username = "<requestor_username>";
        request = CheckoutCartRequest.builder()
                .id(1)
                .username(username)
                .build();

        userCart = Cart.builder()
                .id(1)
                .username(request.getUsername())
                .build();

        cartDetails = CartDetails.builder()
                .id(1)
                .appId("<app_id>")
                .appName("<app_name>")
                .addDate(new Date())
                .appPrice(399399.0)
                .cart(userCart)
                .build();
        userCheckout = Checkout.builder()
                .id(1)
                .statusPembayaran("Menunggu Pembayaran")
                .build();
    }

    @Test
    void testCheckout() {
        doReturn(userCheckout).when(checkoutCartImpl).checkout(request);
        Checkout result = checkoutCartImpl.checkout(request);
        Assertions.assertThat(userCheckout).isEqualTo(result);
    }
}
