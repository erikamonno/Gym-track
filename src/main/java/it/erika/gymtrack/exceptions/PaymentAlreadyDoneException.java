package it.erika.gymtrack.exceptions;

import org.springframework.http.HttpStatusCode;

public class PaymentAlreadyDoneException extends StatusException {

    public PaymentAlreadyDoneException(HttpStatusCode httpStatusCode, String message) {
        super(httpStatusCode, message);
    }
}
