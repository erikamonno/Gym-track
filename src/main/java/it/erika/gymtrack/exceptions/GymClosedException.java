package it.erika.gymtrack.exceptions;

import org.springframework.http.HttpStatusCode;

public class GymClosedException extends StatusException {

    public GymClosedException(HttpStatusCode httpStatusCode, String message) {
        super(httpStatusCode, message);
    }
}
