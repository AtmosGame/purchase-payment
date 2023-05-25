package id.ac.ui.cs.advprog.purchasepayment.usecase;

import id.ac.ui.cs.advprog.purchasepayment.annotations.UseCase;
import id.ac.ui.cs.advprog.purchasepayment.exceptions.AppNotInCartException;
import id.ac.ui.cs.advprog.purchasepayment.ports.CartDetailsRepository;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class DeleteCartImpl implements DeleteCart {
    private final CartDetailsRepository cartDetailsRepository;

    @Override
    public synchronized Void deleteCartByAppId(String username, String appId) {
        // delete cartDetails by username and appId
        boolean isSuccess = cartDetailsRepository.deleteCartDetailsByCartUsernameAndAppId(username, appId) == 1;

        // validate
        if (!isSuccess) {
            throw new AppNotInCartException(appId);
        }

        return null;
    }
}
