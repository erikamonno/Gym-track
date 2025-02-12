package it.erika.gymtrack.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class GymClosedException extends RuntimeException {
    public GymClosedException(String message) {
        super(message);
    }
}
