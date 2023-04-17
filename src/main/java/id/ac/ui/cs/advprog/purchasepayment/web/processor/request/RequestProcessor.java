package id.ac.ui.cs.advprog.purchasepayment.web.processor.request;

public interface RequestProcessor<T> {
    void validate(T request);
}
