package id.ac.ui.cs.advprog.purchasepayment.usecase.CheckPurchased;

import id.ac.ui.cs.advprog.purchasepayment.annotations.UseCase;
import id.ac.ui.cs.advprog.purchasepayment.dto.CheckPurchasedRequest;
import id.ac.ui.cs.advprog.purchasepayment.ports.PurchasedAppRepository;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class CheckPurchasedAppImpl implements CheckPurchasedApp {
    private final PurchasedAppRepository purchasedAppRepository;


    @Override
    public boolean isPurchased(CheckPurchasedRequest request) {
        return existsPurchasedAppByAppId(request.getAppId()) && existsPurchasedAppByUserId(request.getUserId());
    }

    public boolean existsPurchasedAppByUserId(String userId) {
        return purchasedAppRepository.existsPurchasedAppByUserId(userId);
    }

    public boolean existsPurchasedAppByAppId(String appId) {
        return purchasedAppRepository.existsPurchasedAppByAppId(appId);
    }
//
//    @Override
//    public Cart getCartByUsername(String username) {
//        Optional<Cart> optionalUserCart = findCartByUsername(username);
//        return optionalUserCart.orElseGet(() -> {
//            Cart userCart = Cart.builder().username(username).build();
//            return cartRepository.save(userCart);
//        });
//    }
//
//    @Override
//    public Optional<Cart> findCartByUsername(String username) {
//        return cartRepository.findByUsername(username);
//    }
//
//    @Override
//    public CartDetails addCartDetailsToCartByRequest(UpdateCartRequest request, Cart cart) {
//        if (isAppNotInCart(request)) {
//            CartDetails cartDetails = CartDetails.builder()
//                    .appId(request.getId())
//                    .appName(request.getName())
//                    .addDate(new Date())
//                    .appPrice(request.getPrice())
//                    .build();
//            cartDetails.setCart(cart);
//            return cartDetailsRepository.save(cartDetails);
//        } else {
//            // TODO: change to appropriate exception
//            throw new RuntimeException("App already exist in cart");
//        }
//    }
//
//    @Override
//    public Optional<CartDetails> findCartDetailsByCartUsernameAndAppId(String username, String appId) {
//        return cartDetailsRepository.findByCartUsernameAndAppId(username, appId);
//    }
//
//    @Override
//    public boolean isAppNotInCart(UpdateCartRequest request) {
//        Optional<CartDetails> optionalCartDetails = findCartDetailsByCartUsernameAndAppId(
//                request.getUsername(),
//                request.getId());
//        return optionalCartDetails.isEmpty();
//    }
}
