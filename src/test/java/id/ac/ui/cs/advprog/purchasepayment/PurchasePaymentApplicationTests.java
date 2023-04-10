package id.ac.ui.cs.advprog.purchasepayment;

import id.ac.ui.cs.advprog.purchasepayment.controller.DummyController;
import id.ac.ui.cs.advprog.purchasepayment.web.logic.UpdateCartLogic;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.util.AssertionErrors.assertNotNull;


@SpringBootTest
class PurchasePaymentApplicationTests {

    @Autowired
    private DummyController myController;

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void contextLoads() {
        assertThat(myController).isNotNull();
        System.out.println(applicationContext.getBean(DummyController.class));
    }
}
