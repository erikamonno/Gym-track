package it.erika.gymtrack.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AccessNotFoundException extends RuntimeException {
    public AccessNotFoundException(String message) {
        super(message);
    }
}
