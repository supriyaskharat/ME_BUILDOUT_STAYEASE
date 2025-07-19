package com.takehome.stayease.exception.custom_exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class InvalidCredException extends RuntimeException {
  public InvalidCredException(String message) {
    super(message);
  }
}
