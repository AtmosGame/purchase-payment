package id.ac.ui.cs.advprog.purchasepayment.web.processor.response;

public interface ResponseProcessor<T, R> {
    R process(T response);
}
