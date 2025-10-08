package it.erika.gymtrack.exceptions;

import org.springframework.http.HttpStatusCode;

public class CourseNotValidException extends StatusException {

  public CourseNotValidException(HttpStatusCode httpStatusCode, String message) {
    super(httpStatusCode, message);
  }
}

