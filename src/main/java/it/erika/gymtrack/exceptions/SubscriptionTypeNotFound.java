package it.erika.gymtrack.exceptions;

import org.springframework.http.HttpStatusCode;

public class SubscriptionTypeNotFound extends StatusException {

    public SubscriptionTypeNotFound(HttpStatusCode httpStatusCode, String message) {
        super(httpStatusCode, message);
    }
}
