package id.ac.ui.cs.advprog.purchasepayment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PurchasePaymentApplication {

    public static void main(String[] args) {
        SpringApplication.run(PurchasePaymentApplication.class, args);
    }

}
