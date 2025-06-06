package it.erika.gymtrack.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public abstract class StatusException extends RuntimeException {

    private final HttpStatusCode httpStatusCode;

    protected StatusException(HttpStatusCode httpStatusCode, String message) {
        super(message);
        this.httpStatusCode = httpStatusCode;
    }

}
