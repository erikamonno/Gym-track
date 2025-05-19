package it.erika.gymtrack.exceptions;

public class PaymentNotDoneException extends RuntimeException {
    public PaymentNotDoneException(String message) {
        super(message);
    }
}
