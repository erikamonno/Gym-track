package it.erika.gymtrack.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.ResponseStatus;

public class GymClosedException extends StatusException {

    public GymClosedException(HttpStatusCode httpStatusCode, String message) {
        super(httpStatusCode, message);
    }
}
