package id.ac.ui.cs.advprog.purchasepayment.usecase;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

import id.ac.ui.cs.advprog.purchasepayment.dto.CheckoutCartRequest;
import id.ac.ui.cs.advprog.purchasepayment.exceptions.CartIsEmptyException;
import id.ac.ui.cs.advprog.purchasepayment.models.Cart;
import id.ac.ui.cs.advprog.purchasepayment.models.CartDetails;
import id.ac.ui.cs.advprog.purchasepayment.models.Checkout;
import id.ac.ui.cs.advprog.purchasepayment.models.CheckoutDetails;
import id.ac.ui.cs.advprog.purchasepayment.ports.CartDetailsRepository;
import id.ac.ui.cs.advprog.purchasepayment.ports.CartRepository;
import id.ac.ui.cs.advprog.purchasepayment.ports.CheckoutDetailsRepository;
import id.ac.ui.cs.advprog.purchasepayment.ports.CheckoutRepository;
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

    @Mock
    private CartRepository cartRepository;
    @Mock
    private CartDetailsRepository cartDetailsRepository;
    private String username;
    private Checkout userCheckout;
    private Cart userCart;
    private CartDetails cartDetails;
    private CheckoutCartRequest request;
    private CheckoutDetails checkoutDetails;


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

        checkoutDetails = CheckoutDetails.builder()
                .id(1)
                .appId("<app_id>")
                .appName("<app_name>")
                .addDate(new Date())
                .appPrice(399399.0)
                .checkout(userCheckout)
                .build();
    }

    @Test
    void testCheckoutWhenCartIsNotEmpty() {
        // create a mock request with a non-empty cart
        CheckoutCartRequest request = mock(CheckoutCartRequest.class);
        when(request.getUsername()).thenReturn("username");
        Cart cart = new Cart();
        List<CartDetails> cartDetails = List.of(new CartDetails(1, "1", "app1", 100., new Date(), userCart));
        cart.setCartDetails(cartDetails);
        Optional<Cart> optionalCart = Optional.of(cart);
        when(cartRepository.findByUsername("username")).thenReturn(optionalCart);

        // create a mock Checkout object
        LocalDateTime now = LocalDateTime.now();
        Checkout checkout = Checkout.builder()
                .id(1)
                .username("username")
                .statusPembayaran("Menunggu Pembayaran")
                .waktuPembuatanCheckout(now)
                .build();

        // mock the repository methods to return the created objects
        when(checkoutRepository.save(any(Checkout.class))).thenReturn(checkout);

        // Fix: Initialize the mock for checkoutDetailsRepository
        CheckoutDetailsRepository checkoutDetailsRepository = mock(CheckoutDetailsRepository.class);

        // configure the save method of checkoutDetailsRepository to set the id field
        when(checkoutDetailsRepository.save(any(CheckoutDetails.class))).thenAnswer(invocation -> {
            CheckoutDetails savedCheckoutDetails = invocation.getArgument(0);
            savedCheckoutDetails.setId(1); // set the id field manually
            return savedCheckoutDetails;
        });

        // update the constructor invocation in the test setup
        CheckoutCartImpl checkoutCartImpl = new CheckoutCartImpl(
                cartRepository,
                cartDetailsRepository,
                checkoutRepository,
                checkoutDetailsRepository
        );

        // call the checkout method and assert the expected result
        Checkout result = checkoutCartImpl.checkout(request);
        assertEquals(checkout, result);

        // verify that the repository methods were called with the expected arguments
        ArgumentCaptor<Checkout> checkoutArg = ArgumentCaptor.forClass(Checkout.class);
        verify(checkoutRepository).save(checkoutArg.capture());
        assertEquals("username", checkoutArg.getValue().getUsername());
        assertEquals("Menunggu Pembayaran", checkoutArg.getValue().getStatusPembayaran());
        assertTrue(Duration.between(now, checkoutArg.getValue().getWaktuPembuatanCheckout()).getSeconds() < 1);

        ArgumentCaptor<CheckoutDetails> checkoutDetailsArg = ArgumentCaptor.forClass(CheckoutDetails.class);
        verify(checkoutDetailsRepository).save(checkoutDetailsArg.capture());
        assertEquals(1, checkoutDetailsArg.getValue().getId()); // check the id field

        // verify that the deleteByCartUsername method was called with the expected argument
        verify(cartDetailsRepository).deleteByCartUsername("username");
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
    void testValidCheckoutIdReturnsCheckout() {
        // Arrange
        Integer checkoutId = 1;
        Checkout expectedCheckout = new Checkout();
        Mockito.when(checkoutRepository.findById(checkoutId)).thenReturn(Optional.of(expectedCheckout));

        // Act
        Checkout actualCheckout = checkoutCartImpl.getCheckoutById(checkoutId);

        // Assert
        assertEquals(expectedCheckout, actualCheckout);
    }

    @Test
    void testInvalidCheckoutIdReturnsNull() {
        // Arrange
        Integer checkoutId = 100;
        Mockito.when(checkoutRepository.findById(checkoutId)).thenReturn(Optional.empty());

        // Act
        Checkout actualCheckout = checkoutCartImpl.getCheckoutById(checkoutId);

        // Assert
        assertNull(actualCheckout);
    }

}
