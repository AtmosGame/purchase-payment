package id.ac.ui.cs.advprog.purchasepayment.usecase;

import id.ac.ui.cs.advprog.purchasepayment.annotations.UseCase;
import id.ac.ui.cs.advprog.purchasepayment.dto.CheckoutCartRequest;
import id.ac.ui.cs.advprog.purchasepayment.dto.GetCartResponse;
import id.ac.ui.cs.advprog.purchasepayment.dto.UpdateCartRequest;
import id.ac.ui.cs.advprog.purchasepayment.exceptions.AppAlreadyInCartException;
import id.ac.ui.cs.advprog.purchasepayment.exceptions.CartDoesNotExistException;
import id.ac.ui.cs.advprog.purchasepayment.exceptions.CartIsEmptyException;
import id.ac.ui.cs.advprog.purchasepayment.models.Cart;
import id.ac.ui.cs.advprog.purchasepayment.models.CartDetails;
import id.ac.ui.cs.advprog.purchasepayment.models.Checkout;
import id.ac.ui.cs.advprog.purchasepayment.models.CheckoutDetails;
import id.ac.ui.cs.advprog.purchasepayment.ports.CartDetailsRepository;
import id.ac.ui.cs.advprog.purchasepayment.ports.CartRepository;
import id.ac.ui.cs.advprog.purchasepayment.ports.CheckoutDetailsRepository;
import id.ac.ui.cs.advprog.purchasepayment.ports.CheckoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@UseCase
@RequiredArgsConstructor
public class CheckoutCartImpl implements CheckoutCart {
    private final CartRepository cartRepository;
    private final CartDetailsRepository cartDetailsRepository;
    private final CheckoutRepository checkoutRepository;
    private final DeleteCart deleteCart;
    private final CheckoutDetailsRepository checkoutDetailsRepository;

    @Override
    public Checkout checkout(CheckoutCartRequest request) {
        if (checkCartIsEmpty(request.getUsername())){
            throw new CartIsEmptyException(request.getUsername());
        }
        Cart cart = findCartByUsername(request.getUsername()).get();
        List<CartDetails> cartDetails = cart.getCartDetails();
        var userCheckout = Checkout.builder().username(request.getUsername()).statusPembayaran("Menunggu Pembayaran")
                .waktuPembuatanCheckout(LocalDateTime.now()).build();
        Checkout userCheckoutSaved = checkoutRepository.save(userCheckout);
        List<CheckoutDetails> checkoutDetails = addCartDetailsToCheckoutDetails(cartDetails, userCheckoutSaved);
        checkoutDetailsRepository.saveAll(checkoutDetails);

        cartDetailsRepository.deleteByCartUsername(request.getUsername());

        return userCheckoutSaved;
    }

    public List<CheckoutDetails> addCartDetailsToCheckoutDetails(List<CartDetails> request, Checkout checkout) {
        List<CheckoutDetails> checkoutDetailsList = new ArrayList<>();
        for (CartDetails details: request){
            var checkoutDetails = CheckoutDetails.builder()
                    .appId(details.getAppId())
                    .appName(details.getAppName())
                    .addDate(new Date())
                    .appPrice(details.getAppPrice())
                    .checkout(checkout)
                    .build();
            checkoutDetailsRepository.save(checkoutDetails);
            checkoutDetailsList.add(checkoutDetails);
        }
        return checkoutDetailsList;
    }

    @Override
    public GetCartResponse getCartByUsername(String username) {
        var cart = cartRepository.findByUsername(username).orElse(null);

        if (cart == null) {
            throw new CartDoesNotExistException(username);
        }

        List<CartDetails> cartDetails = cartDetailsRepository.findAllByCartId(cart.getId());
        return GetCartResponse.fromCart(cart, cartDetails);
    }

    @Override
    public Checkout getCheckoutByUsername(String username) {
        Optional<Checkout> optionalUserCheckout = checkoutRepository.findByUsername(username);
        return optionalUserCheckout.orElseGet(() -> {
            var userCheckout = Checkout.builder().username(username).build();
            return checkoutRepository.save(userCheckout);
        });
    }

    @Override
    public Optional<Cart> findCartByUsername(String username) {
        return cartRepository.findByUsername(username);
    }

    public boolean checkCartIsEmpty(String username) {
        return findCartByUsername(username)
                .map(Cart::getCartDetails)
                .map(List::isEmpty)
                .orElse(true);
    }

    @Scheduled(fixedRate = 10000)
    public void checkCheckoutTime() {
        var checkouts = checkoutRepository.findAll();
        for (Checkout checkout : checkouts) {
            LocalDateTime threeMinutesLater = checkout.getWaktuPembuatanCheckout().plusMinutes(3);

            if (threeMinutesLater.isBefore(LocalDateTime.now())) {
                checkout.setStatusPembayaran("Expired");
            }
            checkoutRepository.save(checkout);
        }
    }
}
