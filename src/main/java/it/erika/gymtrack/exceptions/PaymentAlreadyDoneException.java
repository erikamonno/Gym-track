package it.erika.gymtrack.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PaymentAlreadyDoneException extends RuntimeException {
    public PaymentAlreadyDoneException(String message) {
        super(message);
    }
}
