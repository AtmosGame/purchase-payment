package id.ac.ui.cs.advprog.purchasepayment.usecase;

import id.ac.ui.cs.advprog.purchasepayment.annotations.UseCase;
import id.ac.ui.cs.advprog.purchasepayment.dto.AddSecretTokenRequest;
import id.ac.ui.cs.advprog.purchasepayment.models.SecretToken;
import id.ac.ui.cs.advprog.purchasepayment.ports.SecretTokenRepository;
import lombok.RequiredArgsConstructor;


@UseCase
@RequiredArgsConstructor
public class AddSecretTokenImpl implements AddSecretToken {

    private final SecretTokenRepository secretTokenRepository;

    @Override
    public SecretToken addSecretTokenByRequest(AddSecretTokenRequest request) {
        var token = new SecretToken();
        token.setTokenName(request.getTokenName());
        return secretTokenRepository.save(token);
    }
}
