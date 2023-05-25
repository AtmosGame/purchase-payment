package id.ac.ui.cs.advprog.purchasepayment.usecase;

import id.ac.ui.cs.advprog.purchasepayment.dto.UpdatePaymentRequest;
import id.ac.ui.cs.advprog.purchasepayment.models.Checkout;
import id.ac.ui.cs.advprog.purchasepayment.models.CheckoutDetails;
import id.ac.ui.cs.advprog.purchasepayment.ports.CheckoutDetailsRepository;
import id.ac.ui.cs.advprog.purchasepayment.ports.CheckoutRepository;
import id.ac.ui.cs.advprog.purchasepayment.ports.PurchasedAppRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdatePaymentImplTest {
    @Spy
    @InjectMocks
    private UpdatePaymentImpl updatePayment;
    @Mock
    private CheckoutRepository checkoutRepository;
    @Mock
    private CheckoutDetailsRepository checkoutDetailsRepository;
    @Mock
    private PurchasedAppRepository purchasedAppRepository;
    @Mock
    private CheckoutCart checkoutCart;

    private UpdatePaymentRequest request;

    @BeforeEach
    void setUp() {
        request = UpdatePaymentRequest.builder()
                .id("1")
                .token("<secret_token>")
                .build();
    }

    @Test
    void testUpdate() {
        doNothing().when(updatePayment).updateCheckoutStatus(anyInt());
        doNothing().when(updatePayment).updateUserPurchasedApps(anyInt());
        updatePayment.update(request);
        verify(updatePayment, times(1)).updateCheckoutStatus(anyInt());
        verify(updatePayment, times(1)).updateUserPurchasedApps(anyInt());
    }

    @Test
    void testUpdateCheckoutStatus() {
        Checkout checkoutExpired = Checkout.builder()
                .id(1)
                .statusPembayaran("Success")
                .build();
        doReturn(checkoutExpired).when(checkoutCart).getCheckoutById(anyInt());

        updatePayment.updateCheckoutStatus(checkoutExpired.getId());
        Assertions.assertThat(checkoutExpired.getStatusPembayaran()).isEqualTo("Success");
    }

    @Test
    void testUpdateUserPurchasedApps() {
        Checkout checkoutExpired = Checkout.builder()
                .id(1)
                .statusPembayaran("Success")
                .username("atmos")
                .build();
        doReturn(checkoutExpired).when(checkoutCart).getCheckoutById(anyInt());

        List<CheckoutDetails> checkoutDetailsList = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            checkoutDetailsList.add(CheckoutDetails.builder()
                    .appId(String.valueOf(i))
                    .appName("app_name" + i)
                    .appPrice(Double.valueOf(i))
                    .addDate(new Date())
                    .build());
        }

        doReturn(checkoutDetailsList).when(checkoutDetailsRepository).findAllByCheckoutId(anyInt());

        updatePayment.updateUserPurchasedApps(checkoutExpired.getId());

        verify(purchasedAppRepository, times(checkoutDetailsList.size())).save(any());
    }

    @Test
    void testUpdateCheckoutStatusIsNull() {
        doReturn(null).when(checkoutCart).getCheckoutById(anyInt());
        updatePayment.updateCheckoutStatus(Integer.valueOf(request.getId()));
        verify(checkoutRepository, times(0)).save(any());
    }

    @Test
    void testUpdateUserPurchasedAppsIsNull() {
        doReturn(null).when(checkoutCart).getCheckoutById(anyInt());
        updatePayment.updateUserPurchasedApps(Integer.valueOf(request.getId()));
        verify(purchasedAppRepository, times(0)).save(any());
    }
}