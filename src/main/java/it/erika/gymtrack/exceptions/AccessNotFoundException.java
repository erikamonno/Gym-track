package it.erika.gymtrack.exceptions;

import org.springframework.http.HttpStatusCode;

public class AccessNotFoundException extends StatusException {

    public AccessNotFoundException(HttpStatusCode httpStatusCode, String message) {
        super(httpStatusCode, message);
    }
}
