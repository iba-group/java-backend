package com.ibagroup.petstore.config.demo;

import java.util.Map;
import lombok.Getter;

public class DemoException extends RuntimeException {

  @Getter
  private Map<String, String> failures;

  public DemoException(Map<String, String> failures) {
    this.failures = failures;
  }
}

