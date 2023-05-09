package id.ac.ui.cs.advprog.purchasepayment.usecase;

import id.ac.ui.cs.advprog.purchasepayment.dto.CheckPurchasedRequest;

public interface CheckPurchasedApp {
    boolean isPurchased (CheckPurchasedRequest request);

}
