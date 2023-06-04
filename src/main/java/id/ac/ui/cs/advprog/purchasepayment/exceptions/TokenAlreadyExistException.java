package id.ac.ui.cs.advprog.purchasepayment.exceptions;

public class TokenAlreadyExistException extends RuntimeException{
    public TokenAlreadyExistException() {
        super("Secret token already exist");
    }
}