package id.ac.ui.cs.advprog.purchasepayment.exceptions;

public class RequestDataInvalidException extends RuntimeException {
    public RequestDataInvalidException() {
        super("Request data is not valid");
    }
}
