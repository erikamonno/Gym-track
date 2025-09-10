package it.erika.gymtrack.exceptions;

import org.springframework.http.HttpStatusCode;

public class CourseNotFoundException extends StatusException {

    public CourseNotFoundException(HttpStatusCode httpStatusCode, String message) {
        super(httpStatusCode, message);
    }
}

