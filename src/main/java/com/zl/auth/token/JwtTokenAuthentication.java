package com.zl.auth.token;

import java.util.Collections;

import org.springframework.security.authentication.AbstractAuthenticationToken;

import com.zl.auth.model.Token;

public class JwtTokenAuthentication extends AbstractAuthenticationToken {

  /**
   * 
   */
  private static final long serialVersionUID = -3689065301970703772L;

  private static final String AUTHENTICATED_ERROR =
      "Authenticated cannot be set true due to the token may be untrusted at this moment in time.";

  private static final boolean SET_UNAUTHORISED = Boolean.FALSE;

  private final Token token;

  public JwtTokenAuthentication(Token token) {
    super(Collections.emptyList());
    this.token = token;
    this.setAuthenticated(SET_UNAUTHORISED);
  }

  @Override
  public void setAuthenticated(boolean authenticated) {
    if (authenticated) {
      throw new IllegalArgumentException(AUTHENTICATED_ERROR);
    }
    super.setAuthenticated(SET_UNAUTHORISED);
  }

  @Override
  public Object getCredentials() {
    return token.getToken();
  }

  @Override
  public Object getPrincipal() {
    return token;
  }

  @Override
  public void eraseCredentials() {
    super.eraseCredentials();
  }
}
