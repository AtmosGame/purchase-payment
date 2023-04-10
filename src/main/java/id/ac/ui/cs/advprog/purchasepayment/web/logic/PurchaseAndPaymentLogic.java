package id.ac.ui.cs.advprog.purchasepayment.web.logic;

public interface PurchaseAndPaymentLogic<T, R> {
    R processLogic(T request);
}
