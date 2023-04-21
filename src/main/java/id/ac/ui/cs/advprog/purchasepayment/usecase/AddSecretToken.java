package id.ac.ui.cs.advprog.purchasepayment.usecase;

import id.ac.ui.cs.advprog.purchasepayment.dto.AddSecretTokenRequest;
import id.ac.ui.cs.advprog.purchasepayment.models.SecretToken;

public interface AddSecretToken {
    SecretToken addSecretTokenByRequest (AddSecretTokenRequest request);
}
