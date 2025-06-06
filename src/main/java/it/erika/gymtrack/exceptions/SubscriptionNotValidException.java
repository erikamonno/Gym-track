package it.erika.gymtrack.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.ResponseStatus;

public class SubscriptionNotValidException extends StatusException {

    public SubscriptionNotValidException(HttpStatusCode httpStatusCode, String message) {
        super(httpStatusCode, message);
    }
}
