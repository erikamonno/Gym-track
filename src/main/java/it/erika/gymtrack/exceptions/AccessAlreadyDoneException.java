package it.erika.gymtrack.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AccessAlreadyDoneException extends RuntimeException {
    public AccessAlreadyDoneException(String message) {
        super(message);
    }
}
