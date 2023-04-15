package id.ac.ui.cs.advprog.purchasepayment.usecase;

import id.ac.ui.cs.advprog.purchasepayment.dto.AddSecretTokenRequest;
import id.ac.ui.cs.advprog.purchasepayment.models.SecretToken;
import id.ac.ui.cs.advprog.purchasepayment.ports.SecretTokenRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AddSecretTokenImplTest {

    @Test
    void testAddSecretTokenByRequest() {
        SecretTokenRepository secretTokenRepository = mock(SecretTokenRepository.class);
        AddSecretTokenRequest request = AddSecretTokenRequest.builder()
                .tokenName("testToken")
                .build();

        SecretToken expectedToken = new SecretToken();
        expectedToken.setId(1);
        expectedToken.setTokenName("testToken");

        when(secretTokenRepository.save(any(SecretToken.class))).thenReturn(expectedToken);

        AddSecretTokenImpl addSecretToken = new AddSecretTokenImpl(secretTokenRepository);

        SecretToken result = addSecretToken.addSecretTokenByRequest(request);

        verify(secretTokenRepository, times(1)).save(any(SecretToken.class));
        assertEquals(expectedToken, result);
    }
}
