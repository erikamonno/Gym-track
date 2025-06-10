package it.erika.gymtrack.exceptions;

import org.springframework.http.HttpStatusCode;

public class SubscriptionNotValidException extends StatusException {

    public SubscriptionNotValidException(HttpStatusCode httpStatusCode, String message) {
        super(httpStatusCode, message);
    }
}
