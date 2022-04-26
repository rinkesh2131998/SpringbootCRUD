package com.problem.predictionbot.exception;

/**
 * exception thrown, for error at uploading csv to databse.
 */
public class CsvFileUploadException extends RuntimeException{
  public CsvFileUploadException(String message) {
    super(message);
  }
}
