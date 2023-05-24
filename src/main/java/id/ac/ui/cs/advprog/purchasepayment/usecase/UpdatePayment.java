package id.ac.ui.cs.advprog.purchasepayment.usecase;

import id.ac.ui.cs.advprog.purchasepayment.dto.UpdatePaymentRequest;

public interface UpdatePayment {
    void update(UpdatePaymentRequest dto);
    void updateCheckoutStatus(Integer checkoutId);
    void updateUserPurchasedApps(Integer checkoutId);
}
