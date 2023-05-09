package id.ac.ui.cs.advprog.purchasepayment.usecase;

import id.ac.ui.cs.advprog.purchasepayment.dto.CheckPurchasedRequest;
import id.ac.ui.cs.advprog.purchasepayment.ports.PurchasedAppRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class CheckPurchasedAppImplTest {
    private CheckPurchasedApp checkPurchasedApp;
    private PurchasedAppRepository purchasedAppRepository;

    @BeforeEach
    void setUp() {
        purchasedAppRepository = mock(PurchasedAppRepository.class);
    }

    @Test
    void testIsPurchasedReturnsTrueWhenAppAndUserExist() {
        String appId = "testAppId";
        String userId = "testUserId";

        CheckPurchasedRequest request = CheckPurchasedRequest.builder()
                .appId(appId)
                .username(userId)
                .build();

        when(purchasedAppRepository.existsPurchasedAppByAppId(appId)).thenReturn(true);
        when(purchasedAppRepository.existsPurchasedAppByUsername(userId)).thenReturn(true);


        checkPurchasedApp = new CheckPurchasedAppImpl(purchasedAppRepository);

        assertTrue(checkPurchasedApp.isPurchased(request));
    }

    @Test
    void testIsPurchasedReturnsFalseWhenAppDoesNotExist() {
        String appId = "testAppId";
        String userId = "testUserId";


        CheckPurchasedRequest request = CheckPurchasedRequest.builder()
                .appId(appId)
                .username(userId)
                .build();

        when(purchasedAppRepository.existsPurchasedAppByAppId(appId)).thenReturn(false);
        when(purchasedAppRepository.existsPurchasedAppByUsername(userId)).thenReturn(true);

        checkPurchasedApp = new CheckPurchasedAppImpl(purchasedAppRepository);

        assertFalse(checkPurchasedApp.isPurchased(request));
    }

    @Test
    void testIsPurchasedReturnsFalseWhenUserDoesNotExist() {
        String appId = "testAppId";
        String userId = "testUserId";

        CheckPurchasedRequest request = CheckPurchasedRequest.builder()
                .appId(appId)
                .username(userId)
                .build();

        when(purchasedAppRepository.existsPurchasedAppByAppId(appId)).thenReturn(true);
        when(purchasedAppRepository.existsPurchasedAppByUsername(userId)).thenReturn(false);

        checkPurchasedApp = new CheckPurchasedAppImpl(purchasedAppRepository);


        assertFalse(checkPurchasedApp.isPurchased(request));
    }
}
