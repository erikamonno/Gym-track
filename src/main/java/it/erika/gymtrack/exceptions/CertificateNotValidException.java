package it.erika.gymtrack.exceptions;

import org.springframework.http.HttpStatusCode;

public class CertificateNotValidException extends StatusException {

    public CertificateNotValidException(HttpStatusCode httpStatusCode, String message) {
        super(httpStatusCode, message);
    }
}
