package id.ac.ui.cs.advprog.purchasepayment.usecase;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.*;

import id.ac.ui.cs.advprog.purchasepayment.dto.CheckoutCartRequest;
import id.ac.ui.cs.advprog.purchasepayment.dto.GetCartResponse;
import id.ac.ui.cs.advprog.purchasepayment.exceptions.CartDoesNotExistException;
import id.ac.ui.cs.advprog.purchasepayment.exceptions.CartIsEmptyException;
import id.ac.ui.cs.advprog.purchasepayment.models.Cart;
import id.ac.ui.cs.advprog.purchasepayment.models.CartDetails;
import id.ac.ui.cs.advprog.purchasepayment.models.Checkout;
import id.ac.ui.cs.advprog.purchasepayment.ports.CartDetailsRepository;
import id.ac.ui.cs.advprog.purchasepayment.ports.CartRepository;
import id.ac.ui.cs.advprog.purchasepayment.ports.CheckoutRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.mockito.Mockito.*;

class CheckoutCartImplTest {

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
    private Cart userCart;
    private CartDetails cartDetails;
    private CheckoutCartRequest request;


    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        username = "<requestor_username>";

        request = CheckoutCartRequest.builder()
                .id(1)
                .username(username)
                .build();

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
    void testCheckoutCartWithEmptyCart() {
        CheckoutCartRequest request = new CheckoutCartRequest(2, "user2");
        assertThrows(CartIsEmptyException.class, () -> {checkoutCartImpl.checkout(request);});
    }

    @Test
    void testCheckCheckoutTime_expired() {
        // arrange
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiredTime = now.minusMinutes(4);
        Checkout checkout = new Checkout();
        checkout.setWaktuPembuatanCheckout(expiredTime);
        checkout.setStatusPembayaran("Menunggu Pembayaran");
        List<Checkout> checkouts = Collections.singletonList(checkout);
        when(checkoutRepository.findAll()).thenReturn(checkouts);
        // act
        checkoutCartImpl.checkCheckoutTime();
        // assert
        verify(checkoutRepository).save(checkout);
        assertEquals("Expired", checkout.getStatusPembayaran());
    }

    @Test
    void testCheckCheckoutTime_notExpired() {
        // arrange
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime notExpiredTime = now.minusMinutes(2);
        Checkout checkout = new Checkout();
        checkout.setWaktuPembuatanCheckout(notExpiredTime);
        checkout.setStatusPembayaran("Menunggu Pembayaran");
        List<Checkout> checkouts = Collections.singletonList(checkout);
        when(checkoutRepository.findAll()).thenReturn(checkouts);
        // act
        checkoutCartImpl.checkCheckoutTime();
        // assert
        verify(checkoutRepository).save(checkout);
        assertEquals("Menunggu Pembayaran", checkout.getStatusPembayaran());
    }

    @Test
    void testCheckCheckoutTime_multipleCheckouts() {
        // arrange
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiredTime1 = now.minusMinutes(4);
        LocalDateTime expiredTime2 = now.minusMinutes(5);
        LocalDateTime notExpiredTime = now.minusMinutes(2);
        Checkout checkout1 = new Checkout();
        checkout1.setWaktuPembuatanCheckout(expiredTime1);
        checkout1.setStatusPembayaran("Menunggu Pembayaran");
        Checkout checkout2 = new Checkout();
        checkout2.setWaktuPembuatanCheckout(expiredTime2);
        checkout2.setStatusPembayaran("Menunggu Pembayaran");
        Checkout checkout3 = new Checkout();
        checkout3.setWaktuPembuatanCheckout(notExpiredTime);
        checkout3.setStatusPembayaran("Menunggu Pembayaran");
        List<Checkout> checkouts = Arrays.asList(checkout1, checkout2, checkout3);
        when(checkoutRepository.findAll()).thenReturn(checkouts);
        // act
        checkoutCartImpl.checkCheckoutTime();
        // assert
        verify(checkoutRepository, times(3)).save(any(Checkout.class));
        assertEquals("Expired", checkout1.getStatusPembayaran());
        assertEquals("Expired", checkout2.getStatusPembayaran());
        assertEquals("Menunggu Pembayaran", checkout3.getStatusPembayaran());
    }

    @Test
    void getCheckoutByUsername_existingCheckout_returnsCheckout() {
        String username = "testuser";
        Checkout expected = Checkout.builder().username(username).build();
        when(checkoutRepository.findByUsername(username)).thenReturn(Optional.of(expected));

        Checkout actual = checkoutCartImpl.getCheckoutByUsername(username);

        assertEquals(expected, actual);
    }

    @Test
    void getCheckoutByUsername_newCheckout_returnsSavedCheckout() {
        String username = "testuser";
        Checkout expected = Checkout.builder().username(username).build();
        when(checkoutRepository.findByUsername(username)).thenReturn(Optional.empty());
        when(checkoutRepository.save(expected)).thenReturn(expected);

        Checkout actual = checkoutCartImpl.getCheckoutByUsername(username);

        assertEquals(expected, actual);
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
        GetCartResponse response = checkoutCartImpl.getCartByUsername(username);

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

        String invalidUsernam = "<invalid_username>";
        Assertions.assertThatThrownBy(() -> checkoutCartImpl.getCartByUsername(invalidUsernam))
                .isInstanceOf(CartDoesNotExistException.class)
                .hasMessageContaining("Cart with username " + invalidUsernam + " does not exist");
    }
}
