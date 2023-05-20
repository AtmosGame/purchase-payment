package id.ac.ui.cs.advprog.purchasepayment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.ui.cs.advprog.purchasepayment.dto.*;
import id.ac.ui.cs.advprog.purchasepayment.dto.auth.User;
import id.ac.ui.cs.advprog.purchasepayment.usecase.JwtService;
import id.ac.ui.cs.advprog.purchasepayment.web.logic.PurchaseAndPaymentLogic;
import org.assertj.core.api.Assertions;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PurchaseAndPaymentController.class)
@AutoConfigureMockMvc
class PurchaseAndPaymentControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JwtService jwtService;

    @Mock
    User user;

    @MockBean
    private PurchaseAndPaymentLogic<UpdateCartRequest, Void> updateCartLogic;

    @MockBean
    private PurchaseAndPaymentLogic<String, GetCartResponse> getCartLogic;

    @MockBean
    private PurchaseAndPaymentLogic<UpdatePaymentRequest, Void> updatePaymentLogic;

    @MockBean
    private PurchaseAndPaymentLogic<AddSecretTokenRequest, Void> addSecretTokenLogic;

    @MockBean
    private PurchaseAndPaymentLogic<CheckoutCartRequest, Void> checkoutCartLogic;

    @MockBean
    private PurchaseAndPaymentLogic<CheckPurchasedRequest, Boolean> checkPurchasedLogic;

    @MockBean
    private PurchaseAndPaymentLogic<DeleteCartRequest, Void> deleteCartLogic;

    @BeforeEach
    void setUp() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(user);
        SecurityContextHolder.setContext(securityContext);
        this.mockMvc  = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @Test
    void testGetTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/test"))
                .andExpect(status().isOk())
                .andReturn();
        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        Assertions.assertThat(actualResponseBody).isEqualTo("PaymentAndPurchaseController Test");
    }

    @Test
    void testUpdateCart() throws Exception {
        UpdateCartRequest updateCartRequest = UpdateCartRequest.builder()
                .id("<app_id>")
                .name("<app_name>")
                .price(0.0)
                .username("<requestor_username>")
                .build();

        mockMvc.perform(put("/api/v1/cart")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(updateCartRequest)))
                .andExpect(status().isOk());

        verify(updateCartLogic, times(1)).processLogic(updateCartRequest);
    }

    @Test
    @WithMockUser(roles = "USER")
    void testGetCart() throws Exception {
        var username = user.getUsername();
        GetCartResponse cartResponse = GetCartResponse.builder()
                .id(1)
                .username(username)
                .build();

        when(getCartLogic.processLogic(username)).thenReturn(cartResponse);

        mockMvc.perform(get("/api/v1/cart")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("getCart"))
                .andExpect(jsonPath("$.id").value(cartResponse.getId()));

        verify(getCartLogic, atLeastOnce()).processLogic(username);
    }

    @Test
    void testUpdatePayment() throws Exception {
        UpdatePaymentRequest updatePaymentRequest = UpdatePaymentRequest.builder()
                .id("<checkout_id>")
                .token("<secret_token>")
                .build();

        mockMvc.perform(put("/api/v1/payment")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(updatePaymentRequest)))
                .andExpect(status().isOk());

        verify(updatePaymentLogic, times(1)).processLogic(updatePaymentRequest);
    }

    @Test
    void testAddSecretToken() throws Exception {
        AddSecretTokenRequest addSecretTokenRequest = AddSecretTokenRequest.builder()
                .tokenName("<secret_token>")
                .build();

        mockMvc.perform(post("/api/v1/add-token")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(addSecretTokenRequest)))
                .andExpect(status().isCreated());

        verify(addSecretTokenLogic, times(1)).processLogic(addSecretTokenRequest);
    }

    @Test
    void testCheckoutCart() throws Exception {
        CheckoutCartRequest checkoutCartRequest = CheckoutCartRequest.builder()
                .id(1)
                .username("<requestor_username>")
                .build();

        mockMvc.perform(post("/api/v1/cart/checkout")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(checkoutCartRequest)))
                .andExpect(status().isCreated());

        verify(checkoutCartLogic, times(1)).processLogic(checkoutCartRequest);
    }

    @Test
    void testCheckPurchasedApp() throws Exception {
        CheckPurchasedRequest request = CheckPurchasedRequest.builder()
                .appId("app-id")
                .username("username")
                .build();

        // when
        mockMvc.perform(post("/api/v1/check-purchased")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn();

        // then
        verify(checkPurchasedLogic, times(1)).processLogic(request);
    }

    @Test
    @WithMockUser(roles = "USER")
    void testDeleteCart() throws Exception {
        DeleteCartRequest deleteCartRequest = DeleteCartRequest.builder()
                .username(user.getUsername())
                .appId("<app_id>")
                .build();

        mockMvc.perform(delete("/api/v1/cart/" + deleteCartRequest.getAppId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("deleteCart"));

        verify(deleteCartLogic, times(1)).processLogic(deleteCartRequest);
    }
}
