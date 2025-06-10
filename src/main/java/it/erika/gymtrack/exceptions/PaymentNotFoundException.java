package it.erika.gymtrack.exceptions;

import org.springframework.http.HttpStatusCode;

public class PaymentNotFoundException extends StatusException {

    public PaymentNotFoundException(HttpStatusCode httpStatusCode, String message) {
        super(httpStatusCode, message);
    }
}
