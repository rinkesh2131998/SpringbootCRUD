package com.problem.predictionbot.exception;

/**
 * exception thrown when uploadding wrong file format.
 */
public class InvalidFileFormatException extends RuntimeException{
  public InvalidFileFormatException(String message) {
    super(message);
  }
}
