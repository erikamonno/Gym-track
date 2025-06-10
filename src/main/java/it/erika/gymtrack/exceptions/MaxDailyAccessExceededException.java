package it.erika.gymtrack.exceptions;

import org.springframework.http.HttpStatusCode;

public class MaxDailyAccessExceededException extends StatusException {

    public MaxDailyAccessExceededException(HttpStatusCode httpStatusCode, String message) {
        super(httpStatusCode, message);
    }
}
