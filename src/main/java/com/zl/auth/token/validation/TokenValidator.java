package com.zl.auth.token.validation;

public interface TokenValidator {
  public String validate(String payload);
}
