package id.ac.ui.cs.advprog.purchasepayment.web.processor.response;

public interface ResponseProcessor<T> {
    void process(T response);
}
