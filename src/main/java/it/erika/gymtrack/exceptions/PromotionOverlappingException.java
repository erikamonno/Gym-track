package it.erika.gymtrack.exceptions;

import org.springframework.http.HttpStatusCode;

public class PromotionOverlappingException extends StatusException {

    public PromotionOverlappingException(HttpStatusCode httpStatusCode, String message) {
        super(httpStatusCode, message);
    }
}
