package com.zl.auth.exception;

public class TokenExpiredException extends IllegalArgumentException {

  /**
   * 
   */
  private static final long serialVersionUID = -4266534022106885418L;

  public TokenExpiredException(String msg) {
    super(msg);
  }

  public TokenExpiredException(String msg, Throwable t) {
    super(msg, t);
  }
}
