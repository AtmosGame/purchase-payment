package id.ac.ui.cs.advprog.purchasepayment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.ui.cs.advprog.purchasepayment.dto.*;
import id.ac.ui.cs.advprog.purchasepayment.usecase.CheckPurchasedApp;
import id.ac.ui.cs.advprog.purchasepayment.web.logic.PurchaseAndPaymentLogic;
import id.ac.ui.cs.advprog.purchasepayment.web.processor.request.RequestProcessor;
import id.ac.ui.cs.advprog.purchasepayment.web.processor.response.ResponseProcessor;
import org.assertj.core.api.Assertions;
import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PurchaseAndPaymentController.class)
@AutoConfigureMockMvc
class PurchaseAndPaymentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PurchaseAndPaymentLogic<UpdateCartRequest, Void> updateCartLogic;

    @MockBean
    private PurchaseAndPaymentLogic<Void, GetCartResponse> getCartLogic;

    @MockBean
    private PurchaseAndPaymentLogic<UpdatePaymentRequest, Void> updatePaymentLogic;

    @MockBean
    private PurchaseAndPaymentLogic<AddSecretTokenRequest, Void> addSecretTokenLogic;

    @MockBean
    private PurchaseAndPaymentLogic<CheckoutCartRequest, Void> checkoutCartLogic;

    @MockBean
    private RequestProcessor<CheckPurchasedRequest> checkPurchasedRequestProcessor;

    @MockBean
    private ResponseProcessor<CheckPurchasedResponse, Boolean> checkPurchasedResponseProcessor;

    @MockBean
    private CheckPurchasedApp checkPurchasedAppImpl;

    @MockBean
    private PurchaseAndPaymentLogic<String, Void> deleteCartLogic;

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
    void testGetCart() throws Exception {
        GetCartResponse getCartResponse = new GetCartResponse();
        getCartResponse.setId(1);

        when(getCartLogic.processLogic(null)).thenReturn(getCartResponse);

        mockMvc.perform(get("/api/v1/cart")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("getCart"))
                .andExpect(jsonPath("$.id").value(String.valueOf(getCartResponse.getId())));

        verify(getCartLogic, atLeastOnce()).processLogic(null);
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
        MvcResult result = mockMvc.perform(post("/api/v1/check-purchased")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn();

        // then
        String content = result.getResponse().getContentAsString();
        boolean isPurchased = objectMapper.readValue(content, Boolean.class);
        verify(checkPurchasedAppImpl, times(1)).isPurchased(request);
        assertEquals(false, isPurchased);
    }

    @Test
    void testDeleteCart() throws Exception {
        var appId = "1";
        mockMvc.perform(delete("/api/v1/cart/" + appId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("deleteCart"));

        verify(deleteCartLogic, times(1)).processLogic(appId);
    }
}
