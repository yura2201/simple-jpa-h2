package com.stackoverflow.mysamples.exception;

/**
 * @author ytsarkov (yurait6@gmail.com) on 20.06.2023
 */
public class OperationException extends RuntimeException {
  public OperationException(String message) {
    super(message);
  }
}
