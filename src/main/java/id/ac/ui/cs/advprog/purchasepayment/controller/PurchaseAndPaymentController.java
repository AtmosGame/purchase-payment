package id.ac.ui.cs.advprog.purchasepayment.controller;

import id.ac.ui.cs.advprog.purchasepayment.dto.UpdateCartRequest;
import id.ac.ui.cs.advprog.purchasepayment.dto.CheckPurchasedRequest;
import id.ac.ui.cs.advprog.purchasepayment.dto.*;
import id.ac.ui.cs.advprog.purchasepayment.dto.auth.User;
import id.ac.ui.cs.advprog.purchasepayment.web.logic.PurchaseAndPaymentLogic;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PurchaseAndPaymentController {
    private final PurchaseAndPaymentLogic<UpdateCartRequest, Void> updateCartLogic;
    private final PurchaseAndPaymentLogic<AddSecretTokenRequest, Void> addSecretTokenLogic;
    private final PurchaseAndPaymentLogic<String, GetCartResponse> getCartLogic;
    private final PurchaseAndPaymentLogic<UpdatePaymentRequest, Void> updatePaymentLogic;
    private final PurchaseAndPaymentLogic<CheckoutCartRequest, Void> checkoutCartLogic;
    private final PurchaseAndPaymentLogic<CheckPurchasedRequest, Boolean> checkPurchasedLogic;
    private final PurchaseAndPaymentLogic<DeleteCartRequest, Void> deleteCartLogic;

    private static User getCurrentUser() {
        return ((User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal());
    }

    @GetMapping("/test")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> sayTest() {
        return ResponseEntity.ok("PaymentAndPurchaseController Test");
    }

    @PutMapping("/cart")
    @PreAuthorize("hasAuthority('cart:update')")
    public ResponseEntity<Void> updateCart(@RequestBody UpdateCartRequest request) {
        var user = getCurrentUser();
        request.setUsername(user.getUsername());
        updateCartLogic.processLogic(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/check-purchased")
    public ResponseEntity<Boolean> checkPurchased(@RequestBody CheckPurchasedRequest request) {
        Boolean isPurchased = checkPurchasedLogic.processLogic(request);
        return new ResponseEntity<>(isPurchased,HttpStatus.CREATED);
    }
    @PostMapping("/add-token")
    public ResponseEntity<Void> addToken(@RequestBody AddSecretTokenRequest request) {
        addSecretTokenLogic.processLogic(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/cart")
    @PreAuthorize("hasAuthority('cart:read_self')")
    public ResponseEntity<GetCartResponse> getCart() {
        var user = getCurrentUser();
        GetCartResponse response = getCartLogic.processLogic(user.getUsername());
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
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/cart/{appId}")
    @PreAuthorize("hasAuthority('cart:delete')")
    public ResponseEntity<Void> deleteCart(@PathVariable String appId) {
        var user = getCurrentUser();
        DeleteCartRequest request = DeleteCartRequest.builder()
                .username(user.getUsername())
                .appId(appId)
                .build();
        deleteCartLogic.processLogic(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
