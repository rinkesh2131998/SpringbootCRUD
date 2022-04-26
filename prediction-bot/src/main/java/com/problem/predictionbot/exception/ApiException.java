package com.problem.predictionbot.exception;

import java.util.Arrays;
import java.util.List;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * response object for errors on apis.
 */
@Data
public class ApiException {
  private HttpStatus status;
  private String message;
  private List<String> errors;

  public ApiException() {
    super();
  }

  public ApiException(HttpStatus status, String message, List<String> errors) {
    super();
    this.status = status;
    this.message = message;
    this.errors = errors;
  }

  public ApiException(HttpStatus status, String message, String errors) {
    super();
    this.status = status;
    this.message = message;
    this.errors = Arrays.asList(errors);
  }
}
