package it.erika.gymtrack.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.ResponseStatus;

public class CertificateNotValidException extends StatusException {

    public CertificateNotValidException(HttpStatusCode httpStatusCode, String message) {
        super(httpStatusCode, message);
    }
}
