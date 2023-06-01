package id.ac.ui.cs.advprog.purchasepayment.usecase;

import id.ac.ui.cs.advprog.purchasepayment.annotations.UseCase;
import id.ac.ui.cs.advprog.purchasepayment.dto.UpdatePaymentRequest;
import id.ac.ui.cs.advprog.purchasepayment.models.CheckoutDetails;
import id.ac.ui.cs.advprog.purchasepayment.models.PurchasedApp;
import id.ac.ui.cs.advprog.purchasepayment.ports.CheckoutDetailsRepository;
import id.ac.ui.cs.advprog.purchasepayment.ports.CheckoutRepository;
import id.ac.ui.cs.advprog.purchasepayment.ports.PurchasedAppRepository;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;

@UseCase
@RequiredArgsConstructor
public class UpdatePaymentImpl implements UpdatePayment {
    private final CheckoutRepository checkoutRepository;
    private final CheckoutDetailsRepository checkoutDetailsRepository;
    private final PurchasedAppRepository purchasedAppRepository;
    private final CheckoutCart checkoutCart;

    @Override
    public void update(UpdatePaymentRequest dto) {
        Integer id = Integer.valueOf(dto.getId());
        updateCheckoutStatus(id);
        updateUserPurchasedApps(id);
    }

    void updateCheckoutStatus(Integer checkoutId) {
        var checkout = checkoutCart.getCheckoutById(checkoutId);

        if (checkout == null) {
            return;
        }

        checkout.setStatusPembayaran("Success");
        checkoutRepository.save(checkout);
    }


    void updateUserPurchasedApps(Integer checkoutId) {
        var checkout = checkoutCart.getCheckoutById(checkoutId);
        List<CheckoutDetails> checkoutDetails = checkoutDetailsRepository.findAllByCheckoutId(checkoutId);

        if (checkout == null) {
            return;
        }

        checkoutDetails.forEach(detail -> {
            var purchasedApp = PurchasedApp.builder()
                    .username(checkout.getUsername())
                    .appId(detail.getAppId())
                    .appName(detail.getAppName())
                    .appPrice(detail.getAppPrice())
                    .purchasedDate(new Date())
                    .build();
            purchasedAppRepository.save(purchasedApp);
        });
    }
}
