package it.erika.gymtrack.exceptions;

import org.springframework.http.HttpStatusCode;

public class PromotionDateNotValidException extends StatusException {

    public PromotionDateNotValidException(HttpStatusCode httpStatusCode, String message) {
        super(httpStatusCode, message);
    }
}
