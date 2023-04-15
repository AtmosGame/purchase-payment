package id.ac.ui.cs.advprog.purchasepayment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.ui.cs.advprog.purchasepayment.dto.UpdateCartRequest;
import id.ac.ui.cs.advprog.purchasepayment.web.logic.PurchaseAndPaymentLogic;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PurchaseAndPaymentController.class)
@AutoConfigureMockMvc
class PurchaseAndPaymentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PurchaseAndPaymentLogic<UpdateCartRequest, Void> updateCartLogic;

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
}