package id.ac.ui.cs.advprog.purchasepayment.exceptions;



public class AppIsNotAvailableException extends RuntimeException{
    public AppIsNotAvailableException() {
        super("App with this id is not available");
    }
}
