package id.ac.ui.cs.advprog.purchasepayment.controller;

import id.ac.ui.cs.advprog.purchasepayment.dto.AddSecretTokenRequest;
import id.ac.ui.cs.advprog.purchasepayment.dto.UpdateCartRequest;
import id.ac.ui.cs.advprog.purchasepayment.web.logic.PurchaseAndPaymentLogic;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PurchaseAndPaymentController {
    private final PurchaseAndPaymentLogic<UpdateCartRequest, Void> updateCartLogic;
    private final PurchaseAndPaymentLogic<AddSecretTokenRequest, Void> addSecretTokenLogic;

    @GetMapping("/test")
    public ResponseEntity<String> sayTest() {
        return ResponseEntity.ok("PaymentAndPurchaseController Test");
    }

    @PutMapping("/cart")
    public ResponseEntity<Void> updateCart(@RequestBody UpdateCartRequest request) {
        updateCartLogic.processLogic(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/add-token")
    public ResponseEntity<Void> addToken(@RequestBody AddSecretTokenRequest request) {
        addSecretTokenLogic.processLogic(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
