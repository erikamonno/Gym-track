package it.erika.gymtrack.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SubscriptionNotValidException extends RuntimeException {
    public SubscriptionNotValidException(String message) {
        super(message);
    }
}
