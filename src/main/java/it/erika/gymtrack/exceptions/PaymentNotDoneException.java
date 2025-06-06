package it.erika.gymtrack.exceptions;

import org.springframework.http.HttpStatusCode;

public class PaymentNotDoneException extends StatusException {

    public PaymentNotDoneException(HttpStatusCode httpStatusCode, String message) {
        super(httpStatusCode, message);
    }
}
