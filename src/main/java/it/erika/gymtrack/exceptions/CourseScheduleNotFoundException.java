package it.erika.gymtrack.exceptions;

import org.springframework.http.HttpStatusCode;

public class CourseScheduleNotFoundException extends StatusException {

    public CourseScheduleNotFoundException(HttpStatusCode httpStatusCode, String message) {
        super(httpStatusCode, message);
    }
}
