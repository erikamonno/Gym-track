package it.erika.gymtrack.exceptions;

import org.springframework.http.HttpStatusCode;

public class SubscriptionNotFoundException extends StatusException {

    public SubscriptionNotFoundException(HttpStatusCode httpStatusCode, String message) {
        super(httpStatusCode, message);
    }
}
