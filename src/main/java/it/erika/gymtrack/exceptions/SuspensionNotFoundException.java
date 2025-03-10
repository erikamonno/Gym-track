package it.erika.gymtrack.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SuspensionNotFoundException extends RuntimeException {
    public SuspensionNotFoundException(String message) {
        super(message);
    }
}
