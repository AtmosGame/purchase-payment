package id.ac.ui.cs.advprog.purchasepayment.usecase;

import id.ac.ui.cs.advprog.purchasepayment.dto.AddSecretTokenRequest;
import id.ac.ui.cs.advprog.purchasepayment.exceptions.TokenAlreadyExistException;
import id.ac.ui.cs.advprog.purchasepayment.models.SecretToken;
import id.ac.ui.cs.advprog.purchasepayment.ports.SecretTokenRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


class AddSecretTokenImplTest {
    @Mock
    private SecretTokenRepository secretTokenRepository;

    @Mock
    private AddSecretTokenImpl addSecretToken;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        addSecretToken = new AddSecretTokenImpl(secretTokenRepository);
    }

    @Test
    void testAddSecretTokenByRequest() {
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

    @Test
    void testAddSecretTokenByRequestTokenAlreadyExist() {
        // Arrange
        AddSecretTokenRequest request = AddSecretTokenRequest.builder()
                .tokenName("secret-token")
                .build();

        when(secretTokenRepository.existsByTokenName(request.getTokenName()))
                .thenReturn(true);

        // Act & Assert
        assertThrows(TokenAlreadyExistException.class,
                () -> addSecretToken.addSecretTokenByRequest(request));

        verify(secretTokenRepository, times(1)).existsByTokenName(request.getTokenName());
        verify(secretTokenRepository, never()).save(any(SecretToken.class));
    }
}

