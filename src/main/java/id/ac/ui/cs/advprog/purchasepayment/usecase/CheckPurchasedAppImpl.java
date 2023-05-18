package id.ac.ui.cs.advprog.purchasepayment.usecase;

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
        return (existsPurchasedAppByAppId(request.getAppId()) && existsPurchasedAppByUsername(request.getUsername()));
    }

    public boolean existsPurchasedAppByUsername(String userId) {
        return purchasedAppRepository.existsPurchasedAppByUsername(userId);
    }

    public boolean existsPurchasedAppByAppId(String appId) {
        return purchasedAppRepository.existsPurchasedAppByAppId(appId);
    }
}