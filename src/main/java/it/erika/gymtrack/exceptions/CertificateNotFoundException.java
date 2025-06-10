package it.erika.gymtrack.exceptions;

import org.springframework.http.HttpStatusCode;

public class CertificateNotFoundException extends StatusException {

    public CertificateNotFoundException(HttpStatusCode httpStatusCode, String message) {
        super(httpStatusCode, message);
    }
}
