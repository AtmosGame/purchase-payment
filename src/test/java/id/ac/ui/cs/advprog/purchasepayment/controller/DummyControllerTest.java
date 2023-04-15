package id.ac.ui.cs.advprog.purchasepayment.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = DummyController.class)
class DummyControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetDummy() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/dummy/hello"))
                .andExpect(status().isOk())
                .andReturn();
        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        Assertions.assertThat(actualResponseBody).isEqualTo("Hello World!");
    }
}