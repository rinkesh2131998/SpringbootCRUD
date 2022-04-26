package com.problem.predictionbot.exception.handler;

import com.problem.predictionbot.exception.ApiException;
import com.problem.predictionbot.exception.CsvFileUploadException;
import com.problem.predictionbot.exception.InvalidFileFormatException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * controller advice for exceptions on rest endpoints.
 */
@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

  // 400

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status,
      final WebRequest request) {
    log.info(ex.getClass().getName());
    //
    final List<String> errors = new ArrayList<String>();
    for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
      errors.add(error.getField() + ": " + error.getDefaultMessage());
    }
    for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
      errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
    }
    final ApiException ApiException =
        new ApiException(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
    return handleExceptionInternal(ex, ApiException, headers, ApiException.getStatus(), request);
  }

  @Override
  protected ResponseEntity<Object> handleBindException(final BindException ex,
                                                       final HttpHeaders headers,
                                                       final HttpStatus status,
                                                       final WebRequest request) {
    log.info(ex.getClass().getName());
    //
    final List<String> errors = new ArrayList<String>();
    for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
      errors.add(error.getField() + ": " + error.getDefaultMessage());
    }
    for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
      errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
    }
    final ApiException ApiException =
        new ApiException(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
    return handleExceptionInternal(ex, ApiException, headers, ApiException.getStatus(), request);
  }

  @Override
  protected ResponseEntity<Object> handleTypeMismatch(final TypeMismatchException ex,
                                                      final HttpHeaders headers,
                                                      final HttpStatus status,
                                                      final WebRequest request) {
    log.info(ex.getClass().getName());
    //
    final String error =
        ex.getValue() + " value for " + ex.getPropertyName() + " should be of type " +
            ex.getRequiredType();

    final ApiException ApiException =
        new ApiException(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
    return new ResponseEntity<Object>(ApiException, new HttpHeaders(), ApiException.getStatus());
  }

  @Override
  protected ResponseEntity<Object> handleMissingServletRequestPart(
      final MissingServletRequestPartException ex, final HttpHeaders headers,
      final HttpStatus status, final WebRequest request) {
    log.info(ex.getClass().getName());
    //
    final String error = ex.getRequestPartName() + " part is missing";
    final ApiException ApiException =
        new ApiException(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
    return new ResponseEntity<Object>(ApiException, new HttpHeaders(), ApiException.getStatus());
  }

  @Override
  protected ResponseEntity<Object> handleMissingServletRequestParameter(
      final MissingServletRequestParameterException ex, final HttpHeaders headers,
      final HttpStatus status, final WebRequest request) {
    log.info(ex.getClass().getName());
    //
    final String error = ex.getParameterName() + " parameter is missing";
    final ApiException ApiException =
        new ApiException(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
    return new ResponseEntity<Object>(ApiException, new HttpHeaders(), ApiException.getStatus());
  }

  //

  /**
   * Handle method argument type mismatch response entity.
   *
   * @param ex      the ex
   * @param request the request
   * @return the response entity
   */
  @ExceptionHandler({MethodArgumentTypeMismatchException.class})
  public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
      final MethodArgumentTypeMismatchException ex, final WebRequest request) {
    log.info(ex.getClass().getName());
    //
    final String error = ex.getName() + " should be of type " + ex.getRequiredType().getName();

    final ApiException ApiException =
        new ApiException(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
    return new ResponseEntity<Object>(ApiException, new HttpHeaders(), ApiException.getStatus());
  }

  // 404

  @Override
  protected ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException ex,
                                                                 final HttpHeaders headers,
                                                                 final HttpStatus status,
                                                                 final WebRequest request) {
    log.info(ex.getClass().getName());
    //
    final String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();

    final ApiException ApiException =
        new ApiException(HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), error);
    return new ResponseEntity<Object>(ApiException, new HttpHeaders(), ApiException.getStatus());
  }

  // 405

  @Override
  protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
      final HttpRequestMethodNotSupportedException ex, final HttpHeaders headers,
      final HttpStatus status, final WebRequest request) {
    log.info(ex.getClass().getName());
    //
    final StringBuilder builder = new StringBuilder();
    builder.append(ex.getMethod());
    builder.append(" method is not supported for this request. Supported methods are ");
    ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));

    final ApiException ApiException =
        new ApiException(HttpStatus.METHOD_NOT_ALLOWED, ex.getLocalizedMessage(),
            builder.toString());
    return new ResponseEntity<Object>(ApiException, new HttpHeaders(), ApiException.getStatus());
  }

  // 415

  @Override
  protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
      final HttpMediaTypeNotSupportedException ex, final HttpHeaders headers,
      final HttpStatus status, final WebRequest request) {
    log.info(ex.getClass().getName());
    //
    final StringBuilder builder = new StringBuilder();
    builder.append(ex.getContentType());
    builder.append(" media type is not supported. Supported media types are ");
    ex.getSupportedMediaTypes().forEach(t -> builder.append(t + " "));

    final ApiException ApiException =
        new ApiException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ex.getLocalizedMessage(),
            builder.substring(0, builder.length() - 2));
    return new ResponseEntity<Object>(ApiException, new HttpHeaders(), ApiException.getStatus());
  }

  // 500

  /**
   * handling NullPointerException.
   *
   * @param nullPointerException exception
   * @param request              api request
   * @return proper response for error
   */
  @ExceptionHandler({NullPointerException.class})
  public ResponseEntity<Object> handleNullPointerException(
      final NullPointerException nullPointerException, final WebRequest request) {
    log.info(nullPointerException.getClass().getName());
    log.error("error: {}", nullPointerException);
    final ApiException ApiException =
        new ApiException(HttpStatus.NO_CONTENT, nullPointerException.getLocalizedMessage(),
            "Null pointer exception occurred");
    return new ResponseEntity<>(ApiException, new HttpHeaders(), ApiException.getStatus());
  }

  /**
   * handling FileNotFoundException.
   *
   * @param fileNotFoundException exception
   * @param request               api request
   * @return proper response for error
   */
  @ExceptionHandler({FileNotFoundException.class})
  public ResponseEntity<Object> handleFileNotFoundException(
      final FileNotFoundException fileNotFoundException, final WebRequest request) {
    log.info(fileNotFoundException.getClass().getName());
    log.error("error: {}", fileNotFoundException);
    final ApiException ApiException =
        new ApiException(HttpStatus.NOT_FOUND, fileNotFoundException.getLocalizedMessage(),
            "File not found");
    return new ResponseEntity<>(ApiException, new HttpHeaders(), ApiException.getStatus());
  }

  /**
   * handling IOException.
   *
   * @param ioException exception
   * @param request     api request
   * @return proper response for error
   */
  @SuppressWarnings("checkstyle:AbbreviationAsWordInName")
  @ExceptionHandler({IOException.class})
  public ResponseEntity<Object> handleIOException(final IOException ioException,
                                                  final WebRequest request) {
    log.info(ioException.getClass().getName());
    log.error("error: {}", ioException);
    final ApiException ApiException =
        new ApiException(HttpStatus.NOT_FOUND, ioException.getLocalizedMessage(),
            "IOException on file operation");
    return new ResponseEntity<>(ApiException, new HttpHeaders(), ApiException.getStatus());
  }

  /**
   * handling IllegalArgumentException.
   *
   * @param illegalArgumentException exception
   * @param request                  api request
   * @return proper response for error
   */
  @ExceptionHandler({IllegalArgumentException.class})
  public ResponseEntity<Object> handleIllegalArgumentException(
      final IllegalArgumentException illegalArgumentException, final WebRequest request) {
    log.info(illegalArgumentException.getClass().getName());
    log.error("error: {}", illegalArgumentException);
    final ApiException ApiException =
        new ApiException(HttpStatus.NOT_FOUND, illegalArgumentException.getLocalizedMessage(),
            "Illegal Argument provided in parameter");
    return new ResponseEntity<>(ApiException, new HttpHeaders(), ApiException.getStatus());
  }

  //custom excecption

  /**
   * handling com.problem.predictionbot.exception.CsvFileUploadException.
   *
   * @param csvFileUploadException exception
   * @param request                api request
   * @return proper response for error
   */
  @ExceptionHandler({CsvFileUploadException.class})
  public ResponseEntity<Object> handleCsvFileUploadException(
      final CsvFileUploadException csvFileUploadException, final WebRequest request) {
    log.info(csvFileUploadException.getClass().getName());
    log.error("error: {}", csvFileUploadException);
    final ApiException ApiException =
        new ApiException(HttpStatus.EXPECTATION_FAILED,
            csvFileUploadException.getLocalizedMessage(),
            "csv file could not be updated to database");
    return new ResponseEntity<>(ApiException, new HttpHeaders(), ApiException.getStatus());
  }

  /**
   * handling com.problem.predictionbot.exception.InvalidFileFormatException.
   *
   * @param invalidFileFormatException exception
   * @param request                    api request
   * @return proper response for error
   */
  @ExceptionHandler({InvalidFileFormatException.class})
  public ResponseEntity<Object> handleInvalidFileFormatException(
      final InvalidFileFormatException invalidFileFormatException, final WebRequest request) {
    log.info(invalidFileFormatException.getClass().getName());
    log.error("error: {}", invalidFileFormatException);
    final ApiException ApiException =
        new ApiException(HttpStatus.BAD_REQUEST, invalidFileFormatException.getLocalizedMessage(),
            "Invalid multipart file format, upload csv");
    return new ResponseEntity<>(ApiException, new HttpHeaders(), ApiException.getStatus());
  }

  /**
   * handling com.problem.predictionbot.exception.MaxUploadSizeExceededException.
   *
   * @param invalidFileFormatException exception
   * @param request                    api request
   * @return proper response for error
   */
  @ExceptionHandler({MaxUploadSizeExceededException.class})
  public ResponseEntity<Object> handleInvalidFileFormatException(
      final MaxUploadSizeExceededException invalidFileFormatException, final WebRequest request) {
    log.info(invalidFileFormatException.getClass().getName());
    log.error("error: {}", invalidFileFormatException);
    final ApiException ApiException =
        new ApiException(HttpStatus.EXPECTATION_FAILED,
            invalidFileFormatException.getLocalizedMessage(),
            "File Size too large for upload");
    return new ResponseEntity<>(ApiException, new HttpHeaders(), ApiException.getStatus());
  }

  /**
   * Handle all response entity.
   *
   * @param ex      the ex
   * @param request the request
   * @return the response entity
   */
  @ExceptionHandler({Exception.class})
  public ResponseEntity<Object> handleAll(final Exception ex, final WebRequest request) {
    log.info(ex.getClass().getName());
    log.error("error", ex);
    //
    final ApiException apiError =
        new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(),
            "error occurred");
    return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
  }

}
