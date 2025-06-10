package it.erika.gymtrack.exceptions;

import org.springframework.http.HttpStatusCode;

public class SuspensionAlreadyExistInThatDateException extends StatusException {

    public SuspensionAlreadyExistInThatDateException(HttpStatusCode httpStatusCode, String message) {
        super(httpStatusCode, message);
    }
}
