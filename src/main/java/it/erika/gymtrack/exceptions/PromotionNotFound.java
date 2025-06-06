package it.erika.gymtrack.exceptions;

import org.springframework.http.HttpStatusCode;

public class PromotionNotFound extends StatusException {

    public PromotionNotFound(HttpStatusCode httpStatusCode, String message) {
        super(httpStatusCode, message);
    }
}
