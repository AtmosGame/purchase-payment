package id.ac.ui.cs.advprog.purchasepayment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PaymentAndPurchaseController {

    @GetMapping("/test")
    public ResponseEntity<String> sayTest() {
        return ResponseEntity.ok("PaymentAndPurchaseController Test");
    }
}
