package id.ac.ui.cs.advprog.purchasepayment.controller;

import id.ac.ui.cs.advprog.purchasepayment.dto.AddSecretTokenRequest;
import id.ac.ui.cs.advprog.purchasepayment.dto.GetCartResponse;
import id.ac.ui.cs.advprog.purchasepayment.dto.UpdateCartRequest;
import id.ac.ui.cs.advprog.purchasepayment.dto.UpdatePaymentRequest;
import id.ac.ui.cs.advprog.purchasepayment.dto.CheckoutCartRequest;
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
    private final PurchaseAndPaymentLogic<Void, GetCartResponse> getCartLogic;
    private final PurchaseAndPaymentLogic<UpdatePaymentRequest, Void> updatePaymentLogic;
    private final PurchaseAndPaymentLogic<CheckoutCartRequest, Void> checkoutCartLogic;

    @GetMapping("/test")
    public ResponseEntity<String> sayTest() {
        return ResponseEntity.ok("PaymentAndPurchaseController Test");
    }

    @PutMapping("/cart")
    public ResponseEntity<Void> updateCart(@RequestBody UpdateCartRequest request) {
        updateCartLogic.processLogic(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/add-token")
    public ResponseEntity<Void> addToken(@RequestBody AddSecretTokenRequest request) {
        addSecretTokenLogic.processLogic(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/cart")
    public ResponseEntity<GetCartResponse> getCart() {
        GetCartResponse response = getCartLogic.processLogic(null);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/payment")
    public ResponseEntity<Void> updatePayment(@RequestBody UpdatePaymentRequest request) {
        updatePaymentLogic.processLogic(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/cart/checkout")
    public ResponseEntity<Void> checkoutCart(@RequestBody CheckoutCartRequest request) {
        checkoutCartLogic.processLogic(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
