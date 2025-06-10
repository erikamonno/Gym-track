package it.erika.gymtrack.exceptions;

import org.springframework.http.HttpStatusCode;

public class SuspensionNotFoundException extends StatusException {

    public SuspensionNotFoundException(HttpStatusCode httpStatusCode, String message) {
        super(httpStatusCode, message);
    }
}
