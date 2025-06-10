package it.erika.gymtrack.exceptions;

import org.springframework.http.HttpStatusCode;

public class PromotionNotFoundException extends StatusException {

    public PromotionNotFoundException(HttpStatusCode httpStatusCode, String message) {
        super(httpStatusCode, message);
    }
}
