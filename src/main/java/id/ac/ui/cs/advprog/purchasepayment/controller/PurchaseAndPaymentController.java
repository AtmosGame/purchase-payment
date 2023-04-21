package id.ac.ui.cs.advprog.purchasepayment.controller;

import id.ac.ui.cs.advprog.purchasepayment.dto.CheckPurchasedResponse;
import id.ac.ui.cs.advprog.purchasepayment.dto.UpdateCartRequest;
import id.ac.ui.cs.advprog.purchasepayment.dto.CheckPurchasedRequest;
import id.ac.ui.cs.advprog.purchasepayment.usecase.CheckPurchased.CheckPurchasedApp;
import id.ac.ui.cs.advprog.purchasepayment.web.logic.CheckPurchasedLogic;
import id.ac.ui.cs.advprog.purchasepayment.web.logic.PurchaseAndPaymentLogic;
import id.ac.ui.cs.advprog.purchasepayment.web.processor.request.RequestProcessor;
import id.ac.ui.cs.advprog.purchasepayment.web.processor.response.ResponseProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PurchaseAndPaymentController {
    private final PurchaseAndPaymentLogic<UpdateCartRequest, Void> updateCartLogic;
    private final PurchaseAndPaymentLogic<CheckPurchasedRequest, Void> checkPurchasedLogic;
    private final RequestProcessor<CheckPurchasedRequest> checkPurchasedRequestProcessor;
    private final ResponseProcessor<CheckPurchasedResponse, Boolean> checkPurchasedResponseProcessor;
    private final CheckPurchasedApp checkPurchasedAppImpl;

    @GetMapping("/test")
    public ResponseEntity<String> sayTest() {
        return ResponseEntity.ok("PaymentAndPurchaseController Test");
    }

    @PutMapping("/cart")
    public ResponseEntity<Void> updateCart(@RequestBody UpdateCartRequest request) {
        updateCartLogic.processLogic(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/check-purchased")
    public ResponseEntity<Boolean> checkPurchased(@RequestBody CheckPurchasedRequest request) {
        CheckPurchasedLogic logic = new CheckPurchasedLogic(checkPurchasedRequestProcessor, checkPurchasedResponseProcessor, checkPurchasedAppImpl);
        logic.processLogic(request);
        boolean isPurchased = logic.getIsPurchased();
        return new ResponseEntity<>(isPurchased,HttpStatus.OK);
    }

}
